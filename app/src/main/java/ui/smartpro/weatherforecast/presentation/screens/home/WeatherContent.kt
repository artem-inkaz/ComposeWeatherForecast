package ui.smartpro.weatherforecast.presentation.screens.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import ui.smartpro.weatherforecast.R
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ui.smartpro.common.Constants
import ui.smartpro.common.theme.AppThemes
import ui.smartpro.domain.models.AppState
import ui.smartpro.domain.models.ErrorState
import ui.smartpro.domain.models.UnitsType
import ui.smartpro.domain.models.WeatherForecast
import ui.smartpro.viewmodels.MainViewModel
import ui.smartpro.weatherforecast.ui.theme.ralewayFontFamily

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@Composable
fun WeatherContent(
    currentTheme: AppThemes,
    weatherForecast: AppState<WeatherForecast>,
    unitsType: UnitsType,
    navigateToPreferencesScreen: () -> Unit,
    navigateToSearchScreen: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState) {
        Box {
            Crossfade(
                targetState = currentTheme.backgroundRes,
                animationSpec = tween(Constants.HOME_SCREEN_BACKGROUND_ANIMATION_DURATION)
            ) {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = stringResource(R.string.weather_background),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
            ) {
                Row(modifier = Modifier
                    .weight(weight = .5f)
                    .fillMaxWidth(),
                    content = {}
                )
                Column(
                    modifier = Modifier
                        .weight(weight = 1f)
                        .fillMaxSize()
                ) {
                    when (weatherForecast) {
                        is AppState.Error -> weatherForecast.message?.let {
                            ErrorContent(errorState = it)
                        }
                        is AppState.Loading -> LoadingContent()
                        is AppState.Success -> weatherForecast.data?.let {
                            // Текущее местоположение
                            LocationContent(
                                weatherForecast = it,
                                currentTheme = currentTheme
                            )
                            // Погода в текущем местоположении
                            CurrentWeatherContent(
                                currentTheme = currentTheme,
                                weatherForecast = it,
                                unitsType = unitsType
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .statusBarsPadding()
            ) {
                // Клик на значок поиска
                IconButton(onClick = { navigateToSearchScreen() }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.icon_settings),
                        modifier = Modifier
                            .size(45.dp)
                            .padding(end = 10.dp),
                        tint = MaterialTheme.colors.primary
                    )
                }
                // Клик на значок настройки
                IconButton(onClick = { navigateToPreferencesScreen() }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(R.string.icon_settings),
                        modifier = Modifier
                            .size(45.dp)
                            .padding(end = 10.dp),
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}

/**
 * Progress bar
  */
@Composable
fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier = Modifier.size(20.dp))
    }
}

/**
 * Обработка ошибки
  */
@Composable
fun ErrorContent(errorState: ErrorState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(fraction = .8f),
            text = when (errorState) {
                ErrorState.NO_INTERNET_CONNECTION -> stringResource(id = R.string.no_internet_connection)
                ErrorState.NO_FORECAST_LOADED -> stringResource(id = R.string.connection_error)
                ErrorState.NO_LOCATION_AVAILABLE -> stringResource(id = R.string.location_error)
                else -> stringResource(id = R.string.unknown_error)
            },
            color = Color.Black,
            fontFamily = ralewayFontFamily,
            fontSize = MaterialTheme.typography.h5.fontSize,
            textAlign = TextAlign.Center,
        )
    }
}

/**
 *  Инфо о текущей локации
 */
@ExperimentalCoroutinesApi
@Composable
fun ColumnScope.LocationContent(
    currentTheme: AppThemes,
    weatherForecast: WeatherForecast,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val forecastUpdateTime by mainViewModel.weatherLastUpdate.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .weight(weight = 1.5f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_location),
            contentDescription = stringResource(R.string.icon_location),
            tint = currentTheme.iconsTint,
            modifier = Modifier
                .size(45.dp)
        )
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = weatherForecast.location,
                color = currentTheme.textColor,
                fontFamily = ralewayFontFamily,
                fontSize = 27.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = if (forecastUpdateTime == 0)
                    stringResource(id = R.string.just_updated)
                else
                    stringResource(id = R.string.updated_time, forecastUpdateTime),
                fontFamily = ralewayFontFamily,
                color = currentTheme.textColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/**
 *  Погода
 */
@ExperimentalCoroutinesApi
@Composable
fun ColumnScope.CurrentWeatherContent(
    currentTheme: AppThemes,
    unitsType: UnitsType,
    weatherForecast: WeatherForecast,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val isForecastLoading by mainViewModel.forecastLoading.collectAsState()

    var currentRotationAngle by remember { mutableStateOf(0f) }
    val rotation = remember { Animatable(currentRotationAngle) }

    LaunchedEffect(key1 = isForecastLoading) {
        if (isForecastLoading) {
            rotation.animateTo(
                targetValue = currentRotationAngle + 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(500, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            ) {
                currentRotationAngle = value
            }
        } else {
            if (currentRotationAngle > 0f) {
                rotation.animateTo(
                    targetValue = currentRotationAngle + 180f,
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                ) {
                    currentRotationAngle = value
                }
            }
        }
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .weight(weight = 1.8f)
    ) {
        Row {
            Text(
                text = weatherForecast.currentWeatherStatus,
                color = currentTheme.textColor,
                fontFamily = ralewayFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp
            )
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = stringResource(R.string.refresh_icon),
                modifier = Modifier
                    .align(Alignment.Top)
                    .rotate(rotation.value)
                    .clickable {
                        if (!rotation.isRunning)
                            mainViewModel.getWeatherForecast()
                    },
                tint = currentTheme.iconsTint
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = weatherForecast.currentWeather + when (unitsType) {
                    UnitsType.METRIC -> "°"
                    else -> "°F"
                },
                color = currentTheme.textColor,
                fontFamily = ralewayFontFamily,
                fontWeight = FontWeight.Light,
                fontSize = 60.sp
            )
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_wind_icon),
                        contentDescription = stringResource(R.string.icon_wind),
                        tint = currentTheme.iconsTint,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(
                                start = 20.dp,
                                top = 0.dp,
                                end = 0.dp,
                                bottom = 0.dp
                            ),
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 5.dp, top = 0.dp, end = 0.dp, bottom = 0.dp),
                        text = weatherForecast.currentWindSpeed + when (unitsType) {
                            UnitsType.METRIC -> stringResource(id = R.string.m_s)
                            else -> stringResource(id = R.string.f_s)
                        },
                        color = currentTheme.textColor,
                        fontFamily = ralewayFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 18.sp
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_humidity),
                        contentDescription = stringResource(R.string.icon_humidity),
                        tint = currentTheme.iconsTint,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(
                                start = 20.dp,
                                top = 0.dp,
                                end = 0.dp,
                                bottom = 0.dp
                            ),
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 5.dp, top = 0.dp, end = 0.dp, bottom = 0.dp),
                        text = weatherForecast.currentHumidity,
                        color = currentTheme.textColor,
                        fontFamily = ralewayFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}