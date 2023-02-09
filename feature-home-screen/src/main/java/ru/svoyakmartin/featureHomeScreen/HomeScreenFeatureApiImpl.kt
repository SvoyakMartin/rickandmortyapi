package ru.svoyakmartin.featureHomeScreen

import androidx.fragment.app.Fragment
import ru.svoyakmartin.featureHomeScreen.ui.fragment.HomeScreenFeatureFlowFragment
import ru.svoyakmartin.featureHomeScreenApi.HomeScreenFeatureApi
import javax.inject.Inject

class HomeScreenFeatureApiImpl @Inject constructor(): HomeScreenFeatureApi {
    override fun getFlowFragment(): Fragment = HomeScreenFeatureFlowFragment()
}