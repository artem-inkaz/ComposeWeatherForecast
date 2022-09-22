package ui.smartpro.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ui.smartpro.common.Constants.CITY_CARD_ANIMATION_DURATION
import ui.smartpro.domain.models.AppState
import ui.smartpro.domain.models.CityItem
import ui.smartpro.domain.models.ForecastDay
import ui.smartpro.domain.models.UnitsType
import ui.smartpro.domain.usecase.cities.FavoriteCitiesUseCaseWrapper
import ui.smartpro.domain.usecase.cities.GetCityList
import ui.smartpro.domain.usecase.forecast.GetDayForecast
import ui.smartpro.domain.usecase.preferences.ReadUnitsSettings
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCityList: GetCityList,
    private val getDayForecast: GetDayForecast,
    private val favoriteCitiesUseCaseWrapper: FavoriteCitiesUseCaseWrapper,
    readUnitsSettings: ReadUnitsSettings
) : ViewModel() {

    private val _searchTextState = MutableStateFlow("")
    val searchTextState = _searchTextState.asStateFlow()

    private val _unitsSettings = MutableStateFlow(UnitsType.METRIC)
    val unitsSettings = _unitsSettings.asStateFlow()

    private val _dayForecast = MutableStateFlow<AppState<ForecastDay>>(AppState.Loading())
    val dayForecast = _dayForecast.asStateFlow()

    private val _favoriteDayForecast = MutableStateFlow<AppState<ForecastDay>>(AppState.Loading())
    val favoriteDayForecast = _favoriteDayForecast.asStateFlow()

    private val _searchedCitiesList =
        MutableStateFlow<AppState<List<CityItem>>>(AppState.Success(listOf()))
    val searchedCitiesList = _searchedCitiesList.asStateFlow()

    private val _almaty =
        MutableStateFlow<AppState<List<CityItem>>>(AppState.Success(listOf()))
    val almaty = _almaty.asStateFlow()

    private val _astana =
        MutableStateFlow<AppState<List<CityItem>>>(AppState.Success(listOf()))
    val astana = _astana.asStateFlow()

    private val _favoriteCitiesList =
        MutableStateFlow<AppState<List<CityItem>>>(AppState.Loading())
    val favoriteCitiesList = _favoriteCitiesList.asStateFlow()

    init {
        readUnitsSettings().onEach { unit ->
            _unitsSettings.value = unit
        }.launchIn(viewModelScope)

        favoriteCitiesUseCaseWrapper.getFavoriteCities().onEach { result ->
            _favoriteCitiesList.value = result
        }.launchIn(viewModelScope)
    }

    fun getCitiesList() = getCityList(searchTextState.value).onEach { result ->
        _searchedCitiesList.value = result
    }.launchIn(viewModelScope)

    fun getAlmaty() = getCityList("Алматы").onEach { result ->
        _almaty.value = result
    }.launchIn(viewModelScope)

    fun getAstana() = getCityList("Нур-Султан").onEach { result ->
        _astana.value = result
    }.launchIn(viewModelScope)

    fun getForecast(coordinates: Pair<Double, Double>) {
        _dayForecast.value = AppState.Loading()
        getDayForecast(
            coordinates.first,
            coordinates.second,
            units = _unitsSettings.value
        ).onEach { result ->
            delay(CITY_CARD_ANIMATION_DURATION)
            _dayForecast.value = result
        }.launchIn(viewModelScope)
    }

    fun getFavoriteCityForecast(coordinates: Pair<Double, Double>) {
        _favoriteDayForecast.value = AppState.Loading()
        getDayForecast(
            coordinates.first,
            coordinates.second,
            units = _unitsSettings.value
        ).onEach { result ->
            delay(CITY_CARD_ANIMATION_DURATION)
            _favoriteDayForecast.value = result
        }.launchIn(viewModelScope)
    }

    fun updateTextState(text: String) {
        _searchTextState.value = text
    }

    fun addCityToFavorite(cityItem: CityItem) = viewModelScope.launch(Dispatchers.IO) {
        favoriteCitiesUseCaseWrapper.addCityToFavorite(cityItem = cityItem)
    }

    fun removeCityFromFavorite(cityItem: CityItem) = viewModelScope.launch(Dispatchers.IO) {
        favoriteCitiesUseCaseWrapper.removeCityFromFavorite(cityItem = cityItem)
    }

}