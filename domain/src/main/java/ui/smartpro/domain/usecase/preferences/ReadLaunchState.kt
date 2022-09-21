package ui.smartpro.domain.usecase.preferences

import ui.smartpro.domain.repositories.datastore.DataStoreRepository
import javax.inject.Inject

class ReadLaunchState @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) {
    operator fun invoke() = dataStoreRepository.readLaunchState
}