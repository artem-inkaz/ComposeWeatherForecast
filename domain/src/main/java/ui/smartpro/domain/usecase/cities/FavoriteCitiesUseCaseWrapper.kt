package ui.smartpro.domain.usecase.cities

import javax.inject.Inject

data class FavoriteCitiesUseCaseWrapper @Inject constructor(
    val addCityToFavorite: AddCityToFavorite,
    val removeCityFromFavorite: RemoveCityFromFavorite,
    val getFavoriteCities: GetFavoriteCities,
    val searchFavoriteCities: SearchFavoriteCities
)