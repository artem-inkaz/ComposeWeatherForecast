package ui.smartpro.data.db.mapper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ui.smartpro.data.db.entities.CityEntity
import ui.smartpro.domain.models.CityItem

fun Flow<List<CityEntity>>.toDomain() = map { entitiesList ->
    entitiesList.map { entity ->
        CityItem(
            name = entity.cityName,
            latitude = entity.latitude,
            longitude = entity.longitude,
            isFavorite = true
        )
    }
}