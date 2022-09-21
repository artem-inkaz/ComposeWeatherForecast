package ui.smartpro.domain.usecase.cities

import ui.smartpro.domain.models.CityItem
import ui.smartpro.domain.repositories.cities.CitiesRepository
import javax.inject.Inject

class RemoveCityFromFavorite @Inject constructor(
    private val citiesRepository: CitiesRepository
) {

    suspend operator fun invoke(cityItem: CityItem) =
        citiesRepository.deleteCity(cityItem = cityItem)

}