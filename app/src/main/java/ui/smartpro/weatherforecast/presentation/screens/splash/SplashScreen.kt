package ui.smartpro.weatherforecast.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import ui.smartpro.common.Constants.SPLASH_SCREEN_FIRST_LAUNCH_DELAY
import ui.smartpro.common.Constants.SPLASH_SCREEN_NORMAL
import ui.smartpro.viewmodels.MainViewModel
import ui.smartpro.weatherforecast.R
import ui.smartpro.weatherforecast.ui.theme.firstGreenBlue

@ExperimentalCoroutinesApi
@Composable
fun SplashScreen (
    mainViewModel: MainViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit,
) {
    val isFirstLaunch by mainViewModel.isFirstLaunch.collectAsState()

    LaunchedEffect(key1 = isFirstLaunch) {
        delay(
            timeMillis = if (isFirstLaunch) SPLASH_SCREEN_FIRST_LAUNCH_DELAY
            else SPLASH_SCREEN_NORMAL
        )
//        navigateToHomeScreen()
    }

    SplashBackground()
}

@Composable
fun SplashBackground(backgroundColor: Color = firstGreenBlue) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = backgroundColor
    ) {

        val systemUiController = rememberSystemUiController()
        SideEffect { systemUiController.setSystemBarsColor(color = firstGreenBlue) }

        Image(
            painterResource(R.mipmap.app_icon),
            contentDescription = stringResource(R.string.splash_screen_background),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.wrapContentSize()
        )
    }
}