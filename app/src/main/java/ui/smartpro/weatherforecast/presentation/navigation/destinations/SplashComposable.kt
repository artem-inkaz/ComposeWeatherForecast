package ui.smartpro.weatherforecast.presentation.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.R
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ui.smartpro.common.Constants.SPLASH_SCREEN
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ui.smartpro.weatherforecast.presentation.screens.splash.SplashScreen
import ui.smartpro.weatherforecast.ui.theme.firstGreenBlue

@ExperimentalCoroutinesApi
@ExperimentalAnimationApi
fun NavGraphBuilder.splashComposable(
    navigateToHomeScreen: () -> Unit,
) {
    composable(
        route = SPLASH_SCREEN,
        exitTransition = {
            fadeOut(animationSpec = tween(durationMillis = 1000))
        }
    ) {
        SplashScreen(navigateToHomeScreen = navigateToHomeScreen)
    }
}

