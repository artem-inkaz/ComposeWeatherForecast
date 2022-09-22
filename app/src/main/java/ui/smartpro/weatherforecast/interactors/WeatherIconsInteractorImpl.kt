package ui.smartpro.weatherforecast.interactors

import ui.smartpro.domain.interactors.WeatherIconsInteractor
import ui.smartpro.weatherforecast.R
import javax.inject.Inject

class WeatherIconsInteractorImpl @Inject constructor() : WeatherIconsInteractor {

    override val cloudsIcon: Int
        get() = R.drawable.ic_cloudy

    override val drizzleIcon: Int
        get() = R.drawable.ic_drizzle

    override val foggyIcon: Int
        get() = R.drawable.ic_windy

    override val rainIcon: Int
        get() = R.drawable.ic_rainy

    override val sunIcon: Int
        get() = R.drawable.ic_sunny

    override val thunderstormIcon: Int
        get() = R.drawable.ic_thunderstorm

    override val snowIcon: Int
        get() = R.drawable.ic_snow

}