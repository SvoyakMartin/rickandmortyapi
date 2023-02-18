package ru.svoyakmartin.featureEpisode.ui.viewModel

import androidx.lifecycle.ViewModel
import ru.svoyakmartin.featureEpisode.di.DaggerEpisodeComponent
import ru.svoyakmartin.featureEpisode.di.EpisodeExternalDependencies

class EpisodeFeatureComponentViewModel : ViewModel() {
    val component by lazy {
        DaggerEpisodeComponent.factory()
            .create(checkNotNull(EpisodeFeatureComponentDependenciesProvider.featureDependencies))
    }

    override fun onCleared() {
        super.onCleared()
        EpisodeFeatureComponentDependenciesProvider.featureDependencies = null
    }
}

object EpisodeFeatureComponentDependenciesProvider {
    var featureDependencies: EpisodeExternalDependencies? = null
}