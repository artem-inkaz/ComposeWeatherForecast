package ui.smartpro.domain.repositories.forecast

import ui.smartpro.domain.models.ForecastDay
import ui.smartpro.domain.models.WeatherForecast

interface ForecastRepository {

    suspend fun getWeeklyForecast(
        lat: Double,
        lon: Double,
        units: String,
        lang: String,
    ): WeatherForecast

    suspend fun getDayForecast(
        lat: Double,
        lon: Double,
        units: String,
        lang: String,
    ) : ForecastDay

}