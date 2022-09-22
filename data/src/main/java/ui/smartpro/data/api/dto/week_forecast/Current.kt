package ui.smartpro.data.api.dto.week_forecast

import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("dt") val dt: Double,
    @SerializedName("sunrise") val sunrise: Double,
    @SerializedName("sunset") val sunset: Double,
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feels_like: Double,
    @SerializedName("pressure") val pressure: Double,
    @SerializedName("humidity") val humidity: Double,
    @SerializedName("dew_point") val dew_point: Double,
    @SerializedName("uvi") val uvi: Double,
    @SerializedName("clouds") val clouds: Double,
    @SerializedName("visibility") val visibility: Double,
    @SerializedName("wind_speed") val wind_speed: Double,
    @SerializedName("wind_deg") val wind_deg: Double,
    @SerializedName("weather") val weather: List<Weather>
)
