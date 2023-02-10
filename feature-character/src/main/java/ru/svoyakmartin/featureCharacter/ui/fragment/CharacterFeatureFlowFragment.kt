package ru.svoyakmartin.featureCharacter.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import ru.svoyakmartin.coreDi.di.dependency.findFeatureExternalDependencies
import ru.svoyakmartin.coreFlow.ui.FlowFragment
import ru.svoyakmartin.coreMvvm.viewModel
import ru.svoyakmartin.coreNavigation.router.flow.FlowRouter
import ru.svoyakmartin.featureCharacter.ui.viewModel.CharacterFeatureComponentDependenciesProvider
import ru.svoyakmartin.featureCharacter.ui.viewModel.CharacterFeatureComponentViewModel
import javax.inject.Inject

class CharacterFeatureFlowFragment : FlowFragment() {
    @Inject
    lateinit var flowRouter: FlowRouter

    override fun onAttach(context: Context) {
        CharacterFeatureComponentDependenciesProvider.featureDependencies = findFeatureExternalDependencies()
        viewModel<CharacterFeatureComponentViewModel>().component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flowRouter.newFlowRootScreen(CharacterListFragment())
    }
}