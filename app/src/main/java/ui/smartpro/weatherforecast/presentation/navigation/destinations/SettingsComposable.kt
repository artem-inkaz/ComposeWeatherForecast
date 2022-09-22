package ui.smartpro.weatherforecast.presentation.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ui.smartpro.common.Constants.SETTINGS_SCREEN
import ui.smartpro.weatherforecast.presentation.screens.settings.SettingsScreen

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@ExperimentalAnimationApi
fun NavGraphBuilder.settingssComposable() {
    composable(route = SETTINGS_SCREEN) { SettingsScreen() }
}