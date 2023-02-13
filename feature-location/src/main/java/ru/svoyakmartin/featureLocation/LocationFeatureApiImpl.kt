package ru.svoyakmartin.featureLocation

import ru.svoyakmartin.featureLocation.data.ExportRepositoryImpl
import ru.svoyakmartin.featureLocation.ui.fragment.LocationDetailsFragment
import ru.svoyakmartin.featureLocation.ui.fragment.LocationFeatureFlowFragment
import ru.svoyakmartin.featureLocationApi.LocationFeatureApi
import javax.inject.Inject

class LocationFeatureApiImpl @Inject constructor(private val repository: ExportRepositoryImpl) :
    LocationFeatureApi {
    override fun getFlowFragment() = LocationFeatureFlowFragment()

    override fun getDetailFragment(locationId: Int) =
        LocationDetailsFragment.newInstance(locationId)

    override fun getLocationMapByIds(locationIdsList: List<Int>) =
        repository.getLocationMapByIds(locationIdsList)
}