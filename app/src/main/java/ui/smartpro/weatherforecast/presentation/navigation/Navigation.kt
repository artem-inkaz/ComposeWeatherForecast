package ui.smartpro.weatherforecast.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ui.smartpro.common.Constants.SPLASH_SCREEN
import ui.smartpro.weatherforecast.presentation.navigation.destinations.homeComposable
import ui.smartpro.weatherforecast.presentation.navigation.destinations.splashComposable

@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@Composable
fun SetupNavigation(navHostController: NavHostController) {
    val screens = remember(navHostController) { Screens(navHostController = navHostController) }

    AnimatedNavHost(navController = navHostController, startDestination = SPLASH_SCREEN) {
        splashComposable(navigateToHomeScreen = screens.splash)
        homeComposable(
            navigateToPreferencesScreen = screens.preferences,
            navigateToSearchScreen = screens.search
        )
    }
}