package ui.smartpro.weatherforecast.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ui.smartpro.domain.models.AppState
import ui.smartpro.viewmodels.SearchViewModel
import ui.smartpro.weatherforecast.presentation.screens.search.EmptyContent

@ExperimentalMaterialApi
@Composable
fun AstanaScreenContent(
    onSearchedCityClicked: (Pair<Double, Double>) -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val astana by searchViewModel.astana.collectAsState()

    val cityDayForecast by searchViewModel.dayForecast.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)

    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            when (astana) {
                is AppState.Error -> EmptyContent()
                is AppState.Loading -> CitiesLoading()
                is AppState.Success -> astana.data?.let { searchedCities ->
                    Spacer(modifier = Modifier.height(10.dp))
                    DisplayCities(
                        cities = searchedCities,
                        cityDayForecast = cityDayForecast,
                        onCityClicked = { onSearchedCityClicked(it) },
                    )
                }
            }
        }
    }
}