package ru.svoyakmartin.featureLocation.data

import kotlinx.coroutines.flow.Flow
import ru.svoyakmartin.featureCore.domain.model.EntityMap

interface ExportRepository {
    fun getLocationMapByIds(locationIdsList: List<Int>): Flow<List<EntityMap>>
}