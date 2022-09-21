package ui.smartpro.domain.usecase.cities

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import ui.smartpro.domain.models.AppState
import ui.smartpro.domain.models.ErrorState
import ui.smartpro.domain.repositories.cities.CitiesRepository
import javax.inject.Inject

class GetFavoriteCities @Inject constructor(
    private val citiesRepository: CitiesRepository
) {
    operator fun invoke() = citiesRepository.getFavoriteCities()
        .catch { exception ->
            Timber.e(exception)
            emit(emptyList())
        }
        .map { cities ->
            if (cities.isNotEmpty()) AppState.Success(data = cities)
            else AppState.Error(error = ErrorState.NO_SAVED_CITIES)
        }
}