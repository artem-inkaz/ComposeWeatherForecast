package ui.smartpro.common

object Constants {
    const val SPLASH_SCREEN_FIRST_LAUNCH_DELAY = 2500L

    const val SPLASH_SCREEN_NORMAL = 700L

    //Screens
    const val SPLASH_SCREEN = "splash"
    const val HOME_SCREEN = "home"

    val thunderstorm_ids_range = 200..232
    val drizzle_ids_range = 300..321
    val rain_ids_range = 500..531
    val snow_ids_range = 600..622
    val atmosphere_ids_range = 701..781
    val clouds_ids_range = 801..804

    const val WEATHER_API_URL = "https://api.openweathermap.org"
    const val CITY_API_URL = "http://geodb-free-service.wirefreethought.com/"
    const val WEATHER_API_ID = "9843cafb088167b60fc8403c09a5ecec"
    const val CITIES_DATABASE_NAME = "cities_database"

    const val PREFERENCE_NAME = "forecast_preferences"
    const val FIRST_LAUNCH_PREFERENCE_KEY = "isFirstLaunch"
    const val UNITS_PREFERENCE_KEY = "units_preference"
}