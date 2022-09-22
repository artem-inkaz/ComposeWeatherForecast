package ui.smartpro.domain.repositories.cities

import kotlinx.coroutines.flow.Flow
import ui.smartpro.domain.models.CityItem

interface CitiesRepository {

    suspend fun getCities(
        namePrefix: String,
        languageCode: String
    ): List<CityItem>

    fun getFavoriteCities(): Flow<List<CityItem>>

    fun searchCities(cityName: String): Flow<List<CityItem>>

    suspend fun saveCity(cityItem: CityItem)

    suspend fun deleteCity(cityItem: CityItem)

}