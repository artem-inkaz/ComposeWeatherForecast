package ui.smartpro.domain.models

data class WeatherForecast(
    val location: String = "",
    val currentWeather: String = "",
    val currentWindSpeed: String = "",
    val currentHumidity: String = "",
    val currentWeatherStatus: String = "",
    val currentWeatherStatusId: Int = -1,
    val forecastDays: List<ForecastDay> = listOf(),
)
