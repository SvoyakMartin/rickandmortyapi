package ru.svoyakmartin.featureCharacter.ui.viewModel

import androidx.lifecycle.ViewModel
import ru.svoyakmartin.coreNavigation.router.flow.FlowRouter
import ru.svoyakmartin.featureCharacter.di.CharacterExternalDependencies
import ru.svoyakmartin.featureCharacter.di.DaggerCharacterComponent

class CharacterFeatureComponentViewModel : ViewModel() {
    val component by lazy {
        DaggerCharacterComponent.factory()
            .create(checkNotNull(CharacterFeatureComponentDependenciesProvider.featureDependencies))
    }

    override fun onCleared() {
        super.onCleared()
        CharacterFeatureComponentDependenciesProvider.featureDependencies = null
    }
}

object CharacterFeatureComponentDependenciesProvider {
    var featureDependencies: CharacterExternalDependencies? = null
}