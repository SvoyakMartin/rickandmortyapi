package ru.svoyakmartin.featureLocation

import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.Flow
import ru.svoyakmartin.featureLocation.data.LocationRepositoryImpl
import ru.svoyakmartin.featureLocation.ui.fragment.LocationDetailsFragment
import ru.svoyakmartin.featureLocation.ui.fragment.LocationFeatureFlowFragment
import ru.svoyakmartin.featureLocationApi.LocationFeatureApi
import javax.inject.Inject

class LocationFeatureApiImpl @Inject constructor(private val repository: LocationRepositoryImpl) :
    LocationFeatureApi {
    override fun getFlowFragment(): Fragment = LocationFeatureFlowFragment()

    override fun getDetailFragment(characterId: Int): Fragment =
        LocationDetailsFragment.newInstance(characterId)

    override fun getLocationMapById(locationId: Int): Flow<Map<String, Int>> =
    repository.getLocationMapById(locationId)

}