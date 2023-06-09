package ru.svoyakmartin.featureEpisode.di

import dagger.Component
import ru.svoyakmartin.coreDi.di.scope.FeatureScope
import ru.svoyakmartin.featureEpisode.ui.fragment.EpisodeDetailsFragment
import ru.svoyakmartin.featureEpisode.ui.fragment.EpisodeListFragment

@[FeatureScope Component(
    modules = [
        EpisodeFeatureApiModule::class,
        EpisodeProvidesModule::class,
        EpisodeBindsModule::class],
    dependencies = [EpisodeExternalDependencies::class]
)]
interface EpisodeComponent {

    @Component.Factory
    interface Factory {
        fun create(dependencies: EpisodeExternalDependencies): EpisodeComponent
    }

    fun inject(fragment: EpisodeListFragment)
    fun inject(fragment: EpisodeDetailsFragment)
}