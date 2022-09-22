package ui.smartpro.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import ui.smartpro.common.Constants.atmosphere_ids_range
import ui.smartpro.common.Constants.clouds_ids_range
import ui.smartpro.common.Constants.drizzle_ids_range
import ui.smartpro.common.Constants.rain_ids_range
import ui.smartpro.common.Constants.snow_ids_range
import ui.smartpro.common.Constants.thunderstorm_ids_range
import ui.smartpro.common.compare
import ui.smartpro.common.location.LocationListener
import ui.smartpro.common.network.ConnectionState
import ui.smartpro.common.network.NetworkStatusListener
import ui.smartpro.common.theme.AppThemes
import ui.smartpro.domain.models.AppState
import ui.smartpro.domain.models.ErrorState
import ui.smartpro.domain.models.UnitsType
import ui.smartpro.domain.models.WeatherForecast
import ui.smartpro.domain.usecase.forecast.GetWeeklyForecast
import ui.smartpro.domain.usecase.preferences.ReadLaunchState
import ui.smartpro.domain.usecase.preferences.ReadUnitsSettings
import ui.smartpro.domain.usecase.preferences.UpdateLaunchState
import ui.smartpro.domain.usecase.preferences.UpdateUnitsSettings
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    readLaunchState: ReadLaunchState,
    saveLaunchState: UpdateLaunchState,
    networkStatusListener: NetworkStatusListener,
    readUnitsSettings: ReadUnitsSettings,
    private val updateUnitsSettings: UpdateUnitsSettings,
    private val getWeeklyForecast: GetWeeklyForecast,
    private val locationListener: LocationListener
) : ViewModel(){

    private val _isFirstLaunch = MutableStateFlow(value = true)
    val isFirstLaunch = _isFirstLaunch.asStateFlow()

    private val _currentTheme = MutableStateFlow<AppThemes>(AppThemes.DefaultTheme())
    val currentTheme = _currentTheme.asStateFlow()

    private val _currentLocation = MutableStateFlow(Pair(-200.0, -200.0))

    private val _forecastLoading = MutableStateFlow(true)
    val forecastLoading = _forecastLoading.asStateFlow()

    private val _weatherForecast = MutableStateFlow<AppState<WeatherForecast>>(AppState.Loading())
    val weatherForecast = _weatherForecast.asStateFlow()

    private val _weatherLastUpdate = MutableStateFlow(value = 0)
    val weatherLastUpdate = _weatherLastUpdate.asStateFlow()

    private val _unitsSettings = MutableStateFlow(UnitsType.METRIC)
    val unitsSettings = _unitsSettings.asStateFlow()

    private val scheduledExecutorService = Executors.newScheduledThreadPool(1)
    private var future: ScheduledFuture<*>? = null

    companion object {
        const val WEATHER_UPDATE_TIMER_PERIOD = 5L
    }

    init {
        readLaunchState().onEach {
            _isFirstLaunch.value = it
            if (it) saveLaunchState()
        }.launchIn(viewModelScope)

        networkStatusListener.networkStatus.onEach { status ->
            when (status) {
                ConnectionState.Available -> {
                    if (_weatherForecast !is AppState.Loading<*>) getWeatherForecast()
                }
                ConnectionState.Unavailable -> {
                    if (_weatherForecast.value.data != null) _weatherForecast.value =
                        AppState.Error(error = ErrorState.NO_INTERNET_CONNECTION)
                }
            }
        }.launchIn(viewModelScope)

        readUnitsSettings().onEach { unit ->
            _unitsSettings.value = unit
            getWeatherForecast()
        }.launchIn(viewModelScope)
    }

    fun observeCurrentLocation() = locationListener.currentLocation.onEach { locationResult ->
        when (locationResult) {
            is AppState.Error -> {
                Timber.e(locationResult.message?.name)
                if (_weatherForecast.value.data == null) {
                    _weatherForecast.value =
                        AppState.Error(error = ErrorState.NO_LOCATION_AVAILABLE)
                }
            }
            is AppState.Loading -> Timber.d(message = "Location loading")
            is AppState.Success -> {
                locationResult.data?.let { coordinates ->
                    if (coordinates.compare(_currentLocation.value)) return@let
                    _currentLocation.value = coordinates
                    getWeatherForecast()
                }
            }
        }
    }.launchIn(viewModelScope)

    fun updateUnitsSetting() = viewModelScope.launch(Dispatchers.IO) {
        updateUnitsSettings(currentUnits = _unitsSettings.value)
    }

    fun getWeatherForecast() {
        getWeeklyForecast(
            lat = _currentLocation.value.first,
            lon = _currentLocation.value.second,
            units = _unitsSettings.value,
            lang = Locale.getDefault().language
        ).onEach { result ->
            when (result) {
                is AppState.Success -> {
                    _currentTheme.value = selectTheme(result.data?.currentWeatherStatusId)
                    _weatherForecast.value = result
                    startForecastUpdateTimer()
                    _forecastLoading.value = false
                }
                is AppState.Loading -> {
                    _forecastLoading.value = true
                    if (future?.isCancelled == false) future?.cancel(false)
                    _weatherLastUpdate.value = 0
                }
                is AppState.Error -> {
                    _forecastLoading.value = false
                    _weatherForecast.value = result
                    Timber.e(result.message?.name)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun startForecastUpdateTimer() {
        future = scheduledExecutorService.scheduleAtFixedRate(
            { _weatherLastUpdate.value += WEATHER_UPDATE_TIMER_PERIOD.toInt() },
            WEATHER_UPDATE_TIMER_PERIOD,
            WEATHER_UPDATE_TIMER_PERIOD,
            TimeUnit.MINUTES
        )
    }

    /**
     *  Когда будут разные темы поменять тему в зависимости от погоды
     */
    private fun selectTheme(currentWeatherStatusId: Int?): AppThemes =
        if (currentWeatherStatusId != null) {
            when (currentWeatherStatusId) {
                in rain_ids_range -> AppThemes.DefaultTheme()
                in clouds_ids_range -> AppThemes.DefaultTheme()
                in atmosphere_ids_range -> AppThemes.DefaultTheme()
                in snow_ids_range -> AppThemes.DefaultTheme()
                in drizzle_ids_range -> AppThemes.DefaultTheme()
                in thunderstorm_ids_range -> AppThemes.DefaultTheme()
                else -> AppThemes.DefaultTheme()
            }
        } else AppThemes.DefaultTheme()

    override fun onCleared() {
        super.onCleared()
        future?.cancel(false)
    }

}