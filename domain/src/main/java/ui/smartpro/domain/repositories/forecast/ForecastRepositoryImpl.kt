package ui.smartpro.domain.repositories.forecast

import dagger.hilt.android.scopes.ViewModelScoped
import ui.smartpro.data.api.ForecastApi
import ui.smartpro.domain.mappers.DtoToDomain
import javax.inject.Inject

@ViewModelScoped
class ForecastRepositoryImpl @Inject constructor(
    private val forecastApi: ForecastApi,
    private val mapper: DtoToDomain
) : ForecastRepository {

    override suspend fun getWeeklyForecast(
        lat: Double,
        lon: Double,
        units: String,
        lang: String,
    ) = mapper.map(forecastApi.getWeekForecast(lat = lat, lon = lon, units = units, lang = lang))

    override suspend fun getDayForecast(
        lat: Double,
        lon: Double,
        units: String,
        lang: String
    ) = mapper.map(forecastApi.getDayForecast(lat = lat, lon = lon, units = units, lang = lang))

}