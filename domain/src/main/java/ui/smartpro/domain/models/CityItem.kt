package ui.smartpro.domain.models

data class CityItem(
    val isFavorite: Boolean = false,
    val name: String = "",
    val longitude: Double = 0.0,
    val latitude: Double = 0.0
)
