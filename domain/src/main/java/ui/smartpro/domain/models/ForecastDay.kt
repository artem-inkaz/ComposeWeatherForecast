package ui.smartpro.domain.models

import androidx.annotation.DrawableRes

data class ForecastDay(
    val dayName: String = "",
    val dayStatus: String = "",
    val dayTemp: String = "",
    val dayStatusId: Int = 0,
    val sunrise: String = "",
    val sunset: String = "",
    val tempFeelsLike: String = "",
    val dayPressure: String = "",
    val dayHumidity: String = "",
    val dayWindSpeed: String = "",
    @DrawableRes val dayIcon: Int = 0,
)
