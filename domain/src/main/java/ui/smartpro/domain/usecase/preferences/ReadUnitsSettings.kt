package ui.smartpro.domain.usecase.preferences

import kotlinx.coroutines.flow.map
import ui.smartpro.domain.models.UnitsType
import ui.smartpro.domain.repositories.datastore.DataStoreRepository
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ReadUnitsSettings @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke() = dataStoreRepository.readUnitsSettings.map {
        try {
            UnitsType.valueOf(it.uppercase())
        } catch (e: IllegalArgumentException) {
            dataStoreRepository.persistUnitsSettings(UnitsType.METRIC.name)
            UnitsType.METRIC
        }
    }
}