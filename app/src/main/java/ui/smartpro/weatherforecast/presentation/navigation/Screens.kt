package ui.smartpro.weatherforecast.presentation.navigation

import androidx.navigation.NavHostController
import ui.smartpro.common.Constants.HOME_SCREEN
import ui.smartpro.common.Constants.SEARCH_SCREEN
import ui.smartpro.common.Constants.SETTINGS_SCREEN
import ui.smartpro.common.Constants.SPLASH_SCREEN

class Screens(navHostController: NavHostController) {
    /**
     *  Экран заставки
     */
    val splash: () -> Unit = {
        navHostController.navigate(route = HOME_SCREEN) {
            popUpTo(SPLASH_SCREEN) { inclusive = true }
        }
    }

    /**
     *  Экран настроек
     */
    val settings: () -> Unit = {
        navHostController.navigate(route = SETTINGS_SCREEN)
    }

    /**
     *  Экран Поиска погоды по городам
     */
    val search: () -> Unit = {
        navHostController.navigate(route = SEARCH_SCREEN)
    }
}