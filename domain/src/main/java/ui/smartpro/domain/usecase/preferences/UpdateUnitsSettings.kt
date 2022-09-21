package ui.smartpro.domain.usecase.preferences

import ui.smartpro.domain.models.UnitsType
import ui.smartpro.domain.repositories.datastore.DataStoreRepository
import javax.inject.Inject

class UpdateUnitsSettings @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(currentUnits: UnitsType) =
        dataStoreRepository.persistUnitsSettings(unitsType = currentUnits.next.name)
}