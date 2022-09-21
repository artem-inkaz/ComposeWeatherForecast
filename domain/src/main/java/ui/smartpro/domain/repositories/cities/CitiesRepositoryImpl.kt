package ui.smartpro.domain.repositories.cities

import dagger.hilt.android.scopes.ViewModelScoped
import ui.smartpro.data.api.CityApi
import ui.smartpro.data.db.dao.CitiesDao
import ui.smartpro.data.db.mapper.toDomain
import ui.smartpro.data.db.mapper.toEntity
import ui.smartpro.domain.mappers.toDomain
import ui.smartpro.domain.models.CityItem
import javax.inject.Inject

@ViewModelScoped
class CitiesRepositoryImpl @Inject constructor(
    private val cityApi: CityApi,
    private val citiesDao: CitiesDao
) : CitiesRepository {

    override suspend fun getCities(
        namePrefix: String,
        languageCode: String
    ) = cityApi.getCityList(namePrefix = namePrefix, languageCode = languageCode).toDomain()

    override fun getFavoriteCities() = citiesDao.getAllCities().toDomain()

    override fun searchCities(cityName: String) =
        citiesDao.searchCities(cityName = cityName).toDomain()

    override suspend fun saveCity(cityItem: CityItem) =
        citiesDao.insertCity(cityEntity = cityItem.toEntity())

    override suspend fun deleteCity(cityItem: CityItem) =
        citiesDao.deleteCity(cityEntity = cityItem.toEntity())

}