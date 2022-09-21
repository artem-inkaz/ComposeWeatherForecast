package ui.smartpro.weatherforecast.presentation.navigation

import androidx.navigation.NavHostController
import ui.smartpro.common.Constants.HOME_SCREEN
import ui.smartpro.common.Constants.SPLASH_SCREEN

class Screens(navHostController: NavHostController)  {

    val splash: () -> Unit = {
        navHostController.navigate(route = HOME_SCREEN) {
            popUpTo(SPLASH_SCREEN) { inclusive = true }
        }
    }
}