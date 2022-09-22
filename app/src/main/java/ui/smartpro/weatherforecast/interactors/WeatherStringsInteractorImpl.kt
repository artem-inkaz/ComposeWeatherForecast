package ui.smartpro.weatherforecast.interactors

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ui.smartpro.domain.interactors.WeatherStringsInteractor
import ui.smartpro.weatherforecast.R
import javax.inject.Inject

class WeatherStringsInteractorImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : WeatherStringsInteractor {

    override val unknown: String
        get() = context.getString(R.string.unknown)

    override val today: String
        get() = context.getString(R.string.today)
}