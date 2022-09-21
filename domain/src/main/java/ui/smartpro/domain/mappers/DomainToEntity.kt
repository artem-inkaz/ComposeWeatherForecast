package ui.smartpro.data.db.mapper

import ui.smartpro.data.db.entities.CityEntity
import ui.smartpro.domain.models.CityItem

fun CityItem.toEntity() = CityEntity(
    cityName = name,
    latitude = latitude,
    longitude = longitude
)