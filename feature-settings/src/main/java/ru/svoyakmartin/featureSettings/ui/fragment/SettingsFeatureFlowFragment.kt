package ru.svoyakmartin.featureSettings.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import ru.svoyakmartin.coreDi.di.dependency.findFeatureExternalDependencies
import ru.svoyakmartin.coreFlow.ui.FlowFragment
import ru.svoyakmartin.coreMvvm.viewModel
import ru.svoyakmartin.coreNavigation.router.flow.FlowRouter
import ru.svoyakmartin.featureSettings.ui.viewModel.SettingsComponentDependenciesProvider
import ru.svoyakmartin.featureSettings.ui.viewModel.SettingsComponentViewModel
import javax.inject.Inject

class SettingsFeatureFlowFragment : FlowFragment() {
    @Inject
    lateinit var flowRouter: FlowRouter

    override fun onAttach(context: Context) {
        SettingsComponentDependenciesProvider.dependencies = findFeatureExternalDependencies()
        viewModel<SettingsComponentViewModel>().component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flowRouter.newFlowRootScreen(SettingsFragment())
    }
}