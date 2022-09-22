package ui.smartpro.weatherforecast.presentation.screens.home

import android.Manifest
import android.content.Intent
import android.provider.Settings
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ui.smartpro.viewmodels.MainViewModel
import ui.smartpro.weatherforecast.R
import ui.smartpro.weatherforecast.presentation.components.PermissionsRequest

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@Composable
fun HomeScreen(
    navigateToPreferencesScreen: () -> Unit,
    navigateToSearchScreen: () -> Unit,
    mainViewmodel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()

    val currentTheme by mainViewmodel.currentTheme.collectAsState()
    val weatherForecast by mainViewmodel.weatherForecast.collectAsState()
    val unitsType by mainViewmodel.unitsSettings.collectAsState()

    LaunchedEffect(key1 = currentTheme) {
        systemUiController.setStatusBarColor(darkIcons = true, color = Color.Transparent)
        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = currentTheme.useDarkNavigationIcons
        )
    }
    /**
     *  Запрос пермишн при первом запуске
     */
    PermissionsRequest(
        permissions = Manifest.permission.ACCESS_FINE_LOCATION,
        permissionDeniedMessage = stringResource(id = R.string.permission_denied_message),
        navigateToSettingsScreen = { context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
    ) {
        LaunchedEffect(key1 = true) { mainViewmodel.observeCurrentLocation() }
        WeatherContent(
            currentTheme = currentTheme,
            weatherForecast = weatherForecast,
            unitsType = unitsType,
            navigateToPreferencesScreen = { navigateToPreferencesScreen() },
            navigateToSearchScreen = { navigateToSearchScreen() }
        )
    }
}