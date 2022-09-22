package ui.smartpro.domain.usecase.cities

import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import ui.smartpro.domain.models.AppState
import ui.smartpro.domain.models.ErrorState
import ui.smartpro.domain.repositories.cities.CitiesRepository
import java.util.*
import javax.inject.Inject

class GetCityList @Inject constructor(
    private val citiesRepository: CitiesRepository
) {
    operator fun invoke(cityName: String) = flow {
        emit(AppState.Loading())
        try {
            val response =
                citiesRepository.getCities(cityName, Locale.getDefault().language)
            if (response.isEmpty()) emit(AppState.Error(error = ErrorState.EMPTY_RESULT))
            else emit(AppState.Success(data = response))
        } catch (exception: HttpException) {
            emit(AppState.Error(error = ErrorState.NO_INTERNET_CONNECTION))
            Timber.e(exception)
        } catch (exception: Exception) {
            emit(AppState.Error(error = ErrorState.NO_INTERNET_CONNECTION))
            Timber.e(exception)
        }
    }
}