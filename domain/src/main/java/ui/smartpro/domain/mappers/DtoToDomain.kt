package ui.smartpro.domain.mappers

import android.content.Context
import android.location.Geocoder
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import ui.smartpro.common.Constants
import ui.smartpro.common.addTempPrefix
import ui.smartpro.data.api.dto.city_search.CitiesResponse
import ui.smartpro.data.api.dto.day_forecast.DayForecastResponse
import ui.smartpro.data.api.dto.week_forecast.Daily
import ui.smartpro.data.api.dto.week_forecast.ForecastResponse
import ui.smartpro.domain.interactors.WeatherIconsInteractor
import ui.smartpro.domain.interactors.WeatherStringsInteractor
import ui.smartpro.domain.models.CityItem
import ui.smartpro.domain.models.ForecastDay
import ui.smartpro.domain.models.WeatherForecast
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DtoToDomain @Inject constructor(
    @ApplicationContext private val context: Context,
    private val weatherIconsInteractor: WeatherIconsInteractor,
    private val weatherStringsInteractor: WeatherStringsInteractor
) {

    companion object {
        private const val TIME_FORMAT = "HH:mm"
        private const val DATE_FORMAT = "EEE\nd/M"
        private const val TODAY_DATE_FORMAT = "d/M"

        private const val DOUBLE_NUMBERS_FORMAT = "%.0f"
    }

    private val timeFormatter = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())

    fun map(weekForecast: ForecastResponse) = WeatherForecast(
        location = getLocationName(lat = weekForecast.lat, lon = weekForecast.lon),
        currentWeather = DOUBLE_NUMBERS_FORMAT.format(weekForecast.current.temp).addTempPrefix(),
        currentWindSpeed = DOUBLE_NUMBERS_FORMAT.format(weekForecast.current.wind_speed),
        currentHumidity = "${DOUBLE_NUMBERS_FORMAT.format(weekForecast.current.humidity)}%",
        currentWeatherStatus = if (weekForecast.current.weather.isNotEmpty())
            weekForecast.current.weather[0].description.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            }
        else
            weatherStringsInteractor.unknown,
        currentWeatherStatusId = if (weekForecast.current.weather.isNotEmpty())
            weekForecast.current.weather[0].id.toInt()
        else
            800,
        forecastDays = weekForecast.daily.map(),
    )

    fun map(dayForecastResponse: DayForecastResponse) = ForecastDay(
        dayName = getTodayDate(),
        dayStatus = dayForecastResponse.weather.first().description,
        dayIcon = selectWeatherStatusIcon(dayForecastResponse.weather.first().id),
        dayTemp = DOUBLE_NUMBERS_FORMAT.format(dayForecastResponse.main.temp),
        dayPressure = DOUBLE_NUMBERS_FORMAT.format(dayForecastResponse.main.pressure / 1.333),
        dayHumidity = "${dayForecastResponse.main.humidity}%",
        dayWindSpeed = DOUBLE_NUMBERS_FORMAT.format(dayForecastResponse.wind.speed),
        sunrise = timeFormatter.format("${dayForecastResponse.sys.sunrise}000".toLong()),
        sunset = timeFormatter.format("${dayForecastResponse.sys.sunset}000".toLong())
    )

    private fun List<Daily>.map() = mapIndexed { index, day ->
        ForecastDay(
            dayName = getDayName(index),
            dayStatus = if (day.weather.isNotEmpty())
                day.weather[0].description
            else
                weatherStringsInteractor.unknown,
            dayTemp = if (day.weather.isNotEmpty())
                DOUBLE_NUMBERS_FORMAT.format(day.temp.day).addTempPrefix()
            else
                weatherStringsInteractor.unknown,
            dayStatusId = if (day.weather.isNotEmpty()) day.weather.first().id.toInt() else 800,
            sunrise = timeFormatter.format("${day.sunrise}000".toLong()),
            sunset = timeFormatter.format("${day.sunset}000".toLong()),
            tempFeelsLike = DOUBLE_NUMBERS_FORMAT.format(day.feels_like.day).addTempPrefix(),
            dayPressure = DOUBLE_NUMBERS_FORMAT.format(day.pressure / 1.333),
            dayHumidity = day.humidity.toString(),
            dayWindSpeed = day.wind_speed.toString(),
            dayIcon = selectWeatherStatusIcon(day.weather.first().id.toInt())
        )
    }

    private fun getLocationName(
        lat: Double,
        lon: Double
    ): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        return try {
            val addresses = geocoder.getFromLocation(lat, lon, 1)
            addresses.first().subAdminArea
                ?: addresses.first().adminArea
                ?: addresses.first().locality
        } catch (e: Exception) {
            Timber.e(e)
            weatherStringsInteractor.unknown
        }
    }

    private fun getDayName(dayIndex: Int): String =
        if (dayIndex == 0) "${weatherStringsInteractor.today}\n ${
            SimpleDateFormat(
                TODAY_DATE_FORMAT,
                Locale.getDefault()
            ).format(Calendar.getInstance().time)
        }"
        else {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, dayIndex)
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(calendar.time)
                .replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
        }

    private fun getTodayDate() = SimpleDateFormat(
        TODAY_DATE_FORMAT,
        Locale.getDefault()
    ).format(Calendar.getInstance().time)

    private fun selectWeatherStatusIcon(weatherStatusId: Int) = when (weatherStatusId) {
        in Constants.rain_ids_range -> weatherIconsInteractor.rainIcon
        in Constants.clouds_ids_range -> weatherIconsInteractor.cloudsIcon
        in Constants.atmosphere_ids_range -> weatherIconsInteractor.foggyIcon
        in Constants.snow_ids_range -> weatherIconsInteractor.snowIcon
        in Constants.drizzle_ids_range -> weatherIconsInteractor.drizzleIcon
        in Constants.thunderstorm_ids_range -> weatherIconsInteractor.thunderstormIcon
        else -> weatherIconsInteractor.sunIcon
    }
}

fun CitiesResponse.toDomain(): List<CityItem> = data.map { city ->
    CityItem(
        name = "${city.name}, ${city.country}",
        longitude = city.longitude,
        latitude = city.latitude
    )
}.distinctBy { it.name }