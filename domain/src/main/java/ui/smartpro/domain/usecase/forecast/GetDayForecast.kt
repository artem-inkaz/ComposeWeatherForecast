package ui.smartpro.domain.usecase.forecast

import kotlinx.coroutines.flow.flow
import timber.log.Timber
import ui.smartpro.domain.models.AppState
import ui.smartpro.domain.models.ErrorState
import ui.smartpro.domain.models.UnitsType
import ui.smartpro.domain.repositories.forecast.ForecastRepository
import java.util.*
import javax.inject.Inject

class GetDayForecast @Inject constructor(
    private val forecastRepository: ForecastRepository
) {
    operator fun invoke(lat: Double, lon: Double, units: UnitsType) = flow {
        try {
            val dayForecast = forecastRepository.getDayForecast(
                lat = lat,
                lon = lon,
                units = units.name.lowercase(),
                lang = Locale.getDefault().language
            )
            emit(AppState.Success(data = dayForecast))
        } catch (e: Exception) {
            Timber.e(e)
            emit(AppState.Error(error = ErrorState.NO_FORECAST_LOADED))
        }
    }
}