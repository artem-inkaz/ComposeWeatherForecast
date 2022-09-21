package ui.smartpro.domain.usecase.preferences

import ui.smartpro.domain.repositories.datastore.DataStoreRepository
import javax.inject.Inject

class UpdateLaunchState @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke() = dataStoreRepository.persistLaunchState()
}