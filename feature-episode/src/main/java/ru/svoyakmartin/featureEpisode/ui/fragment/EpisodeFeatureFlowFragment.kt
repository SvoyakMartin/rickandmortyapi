package ru.svoyakmartin.featureEpisode.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import ru.svoyakmartin.coreDi.di.dependency.findFeatureExternalDependencies
import ru.svoyakmartin.coreFlow.ui.FlowFragment
import ru.svoyakmartin.coreMvvm.viewModel
import ru.svoyakmartin.coreNavigation.router.flow.FlowRouter
import ru.svoyakmartin.featureEpisode.ui.viewModel.EpisodeFeatureComponentDependenciesProvider
import ru.svoyakmartin.featureEpisode.ui.viewModel.EpisodeFeatureComponentViewModel
import javax.inject.Inject

class EpisodeFeatureFlowFragment : FlowFragment() {
    @Inject
    lateinit var flowRouter: FlowRouter

    override fun onAttach(context: Context) {
        EpisodeFeatureComponentDependenciesProvider.featureDependencies = findFeatureExternalDependencies()
        viewModel<EpisodeFeatureComponentViewModel>().component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flowRouter.newFlowRootScreen(EpisodeListFragment())
    }
}