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
    fun bindCharacterExternalDependencies(appComponent: AppComponent): FeatureExternalDependencies

    @Binds
    @IntoMap
    @FeatureExternalDependenciesKey(HomeScreenExternalDependencies::class)
    fun bindHomeScreenExternalDependencies(appComponent: AppComponent): FeatureExternalDependencies

    @Binds
    @IntoMap
    @FeatureExternalDependenciesKey(SettingsExternalDependencies::class)
    fun bindSettingsExternalDependencies(appComponent: AppComponent): FeatureExternalDependencies

    @Binds
    @IntoMap
    @FeatureExternalDependenciesKey(EpisodeExternalDependencies::class)
    fun bindEpisodeExternalDependencies(appComponent: AppComponent): FeatureExternalDependencies

    @Binds
    @IntoMap
    @FeatureExternalDependenciesKey(LocationExternalDependencies::class)
    fun bindLocationExternalDependencies(appComponent: AppComponent): FeatureExternalDependencies
}