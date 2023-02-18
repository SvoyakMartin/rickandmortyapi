package ru.svoyakmartin.rickandmortyapi.di.modules

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.svoyakmartin.coreDi.di.dependency.FeatureExternalDependencies
import ru.svoyakmartin.coreDi.di.dependency.FeatureExternalDependenciesKey
import ru.svoyakmartin.featureCharacter.di.CharacterExternalDependencies
import ru.svoyakmartin.featureEpisode.di.EpisodeExternalDependencies
import ru.svoyakmartin.featureHomeScreen.di.HomeScreenExternalDependencies
import ru.svoyakmartin.featureLocation.di.LocationExternalDependencies
import ru.svoyakmartin.featureSettings.di.SettingsExternalDependencies
import ru.svoyakmartin.rickandmortyapi.di.AppComponent

@Module
interface FeatureExternalDependenciesModule {

    @Binds
    @IntoMap
    @FeatureExternalDependenciesKey(CharacterExternalDependencies::class)
    @Suppress("unused")
    fun bindCharacterExternalDependencies(appComponent: AppComponent): FeatureExternalDependencies

    @Binds
    @IntoMap
    @FeatureExternalDependenciesKey(HomeScreenExternalDependencies::class)
    @Suppress("unused")
    fun bindHomeScreenExternalDependencies(appComponent: AppComponent): FeatureExternalDependencies

    @Binds
    @IntoMap
    @FeatureExternalDependenciesKey(SettingsExternalDependencies::class)
    @Suppress("unused")
    fun bindSettingsExternalDependencies(appComponent: AppComponent): FeatureExternalDependencies

    @Binds
    @IntoMap
    @FeatureExternalDependenciesKey(EpisodeExternalDependencies::class)
    @Suppress("unused")
    fun bindEpisodeExternalDependencies(appComponent: AppComponent): FeatureExternalDependencies

    @Binds
    @IntoMap
    @FeatureExternalDependenciesKey(LocationExternalDependencies::class)
    @Suppress("unused")
    fun bindLocationExternalDependencies(appComponent: AppComponent): FeatureExternalDependencies
}