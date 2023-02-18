package ru.svoyakmartin.featureLocationApi

import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.Flow
import ru.svoyakmartin.featureCore.domain.model.EntityMap

interface LocationFeatureApi {
    fun getFlowFragment(): Fragment
    fun getDetailFragment(locationId: Int): Fragment
    fun getLocationMapByIds(locationIdsList: List<Int>): Flow<List<EntityMap>>

    companion object {
        const val BACKSTACK_KEY = "locations"
    }
}