package ru.svoyakmartin.featureHomeScreen.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import ru.svoyakmartin.coreDi.di.dependency.findFeatureExternalDependencies
import ru.svoyakmartin.coreFlow.ui.FlowFragment
import ru.svoyakmartin.coreMvvm.viewModel
import ru.svoyakmartin.coreNavigation.router.flow.FlowRouter
import ru.svoyakmartin.featureHomeScreen.ui.viewModel.HomeScreenComponentDependenciesProvider
import ru.svoyakmartin.featureHomeScreen.ui.viewModel.HomeScreenComponentViewModel
import javax.inject.Inject

class HomeScreenFeatureFlowFragment : FlowFragment() {
    @Inject
    lateinit var flowRouter: FlowRouter

    override fun onAttach(context: Context) {
        HomeScreenComponentDependenciesProvider.dependencies = findFeatureExternalDependencies()
        viewModel<HomeScreenComponentViewModel>().component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flowRouter.newFlowRootScreen(HomeScreenFragment())
    }
}