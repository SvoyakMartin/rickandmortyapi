package ru.svoyakmartin.featureLocation.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import ru.svoyakmartin.coreDi.di.dependency.findFeatureExternalDependencies
import ru.svoyakmartin.coreFlow.ui.FlowFragment
import ru.svoyakmartin.coreMvvm.viewModel
import ru.svoyakmartin.coreNavigation.router.flow.FlowRouter
import ru.svoyakmartin.featureLocation.ui.viewModel.LocationFeatureComponentDependenciesProvider
import ru.svoyakmartin.featureLocation.ui.viewModel.LocationFeatureComponentViewModel
import javax.inject.Inject

class LocationFeatureFlowFragment : FlowFragment() {
    @Inject
    lateinit var flowRouter: FlowRouter

    override fun onAttach(context: Context) {
        LocationFeatureComponentDependenciesProvider.featureDependencies = findFeatureExternalDependencies()
        viewModel<LocationFeatureComponentViewModel>().component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flowRouter.newFlowRootScreen(LocationListFragment())
    }
}