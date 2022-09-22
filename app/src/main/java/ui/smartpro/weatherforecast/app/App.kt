package ui.smartpro.weatherforecast.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import ui.smartpro.weatherforecast.BuildConfig

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}