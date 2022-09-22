package ui.smartpro.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ui.smartpro.common.Constants.WEATHER_API_ID
import ui.smartpro.data.api.dto.day_forecast.DayForecastResponse
import ui.smartpro.data.api.dto.week_forecast.ForecastResponse
import javax.inject.Singleton

@Singleton
interface ForecastApi {

    @GET(value = "/data/2.5/onecall")
    suspend fun getWeekForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("lang") lang: String,
        @Query("appid") appid: String = WEATHER_API_ID
    ) : ForecastResponse

    @GET(value = "/data/2.5/weather")
    suspend fun getDayForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("lang") lang: String,
        @Query("appid") appid: String = WEATHER_API_ID
    ): DayForecastResponse
}