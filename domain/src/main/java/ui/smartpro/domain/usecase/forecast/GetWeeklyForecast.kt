package ui.smartpro.domain.usecase.forecast

import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import ui.smartpro.domain.models.AppState
import ui.smartpro.domain.models.ErrorState
import ui.smartpro.domain.models.UnitsType
import ui.smartpro.domain.repositories.forecast.ForecastRepository
import javax.inject.Inject

class GetWeeklyForecast @Inject constructor(
    private val forecastRepository: ForecastRepository
) {
    operator fun invoke(
        lat: Double,
        lon: Double,
        units: UnitsType,
        lang: String,
    ) = flow {
        emit(AppState.Loading())
        try {
            val response =
                forecastRepository.getWeeklyForecast(lat, lon, units.name.lowercase(), lang)
            emit(AppState.Success(data = response))
        } catch (exception: HttpException) {
            if (exception.code() != 400)
                emit(AppState.Error(error = ErrorState.NO_INTERNET_CONNECTION))
            Timber.e(exception)
        } catch (exception: Exception) {
            emit(AppState.Error(error = ErrorState.NO_INTERNET_CONNECTION))
            Timber.e(exception)
        }
    }


}