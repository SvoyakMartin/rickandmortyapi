package ru.svoyakmartin.featureLocationApi

import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.Flow

interface LocationFeatureApi {
    fun getFlowFragment(): Fragment
    fun getDetailFragment(locationId: Int): Fragment
    fun getLocationMapById(locationId: Int): Flow<Map<String, Int>>

    companion object {
        const val BACKSTACK_KEY = "locations"
    }
}