package ru.svoyakmartin.featureHomeScreen

import ru.svoyakmartin.featureHomeScreen.ui.fragment.HomeScreenFragment
import ru.svoyakmartin.featureHomeScreenApi.HomeScreenFeatureApi
import javax.inject.Inject

class HomeScreenFeatureApiImpl @Inject constructor(): HomeScreenFeatureApi {
    override fun getFlowFragment() = HomeScreenFragment()
}