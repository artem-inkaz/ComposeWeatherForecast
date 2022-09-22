package ui.smartpro.weatherforecast.presentation.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ui.smartpro.viewmodels.SearchViewModel
import ui.smartpro.weatherforecast.ui.theme.secondYellowDawn
import ui.smartpro.weatherforecast.R

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    val systemUiController = rememberSystemUiController()
    val searchedTextState by searchViewModel.searchTextState.collectAsState()

    SideEffect() {
        systemUiController.setStatusBarColor(darkIcons = true, color = secondYellowDawn)
        systemUiController.setNavigationBarColor(color = secondYellowDawn, darkIcons = true)
    }

    Box(modifier = Modifier.systemBarsPadding()) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                // Строка поиска ввода названия города
                SearchAppBar(
                    searchedTextState = searchedTextState,
                    onTextChange = { searchViewModel.updateTextState(it) },
                    onSearchClicked = { searchViewModel.getCitiesList() })
            },
            content = {
                // Информация о городе
                SearchScreenContent(
                    onSearchedCityClicked = { coordinates ->
                        searchViewModel.getForecast(coordinates = coordinates)
                    }, onFavoriteCityClicked = { coordinates ->
                        searchViewModel.getFavoriteCityForecast(coordinates = coordinates)
                    },
                    onAddCityToFavoriteClicked = { city ->
                        searchViewModel.addCityToFavorite(cityItem = city)
                    },
                    onSwipeToRemove = { city ->
                        searchViewModel.removeCityFromFavorite(cityItem = city)
                    })
            },
            floatingActionButton = {
                // FAB кнопка внизу экрана
                SearchCityFab(onSearchClicked = searchViewModel::getCitiesList)
            }
        )
    }
}

@Composable
fun SearchCityFab(onSearchClicked: () -> Unit) {
    FloatingActionButton(
        onClick = { onSearchClicked() },
        backgroundColor = secondYellowDawn
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(R.string.search_icon),
            tint = Color.White
        )
    }
}