package ru.svoyakmartin.featureLocation.di

import dagger.Component
import ru.svoyakmartin.coreDi.di.scope.FeatureScope
import ru.svoyakmartin.coreFlow.di.FlowModule
import ru.svoyakmartin.featureLocation.LocationFeatureApiImpl
import ru.svoyakmartin.featureLocation.ui.fragment.LocationDetailsFragment
import ru.svoyakmartin.featureLocation.ui.fragment.LocationFeatureFlowFragment
import ru.svoyakmartin.featureLocation.ui.fragment.LocationListFragment

@[FeatureScope Component(
    modules = [
        LocationFeatureApiModule::class,
        LocationProvidesModule::class,
        LocationBindsModule::class,
        FlowModule::class],
    dependencies = [LocationExternalDependencies::class]
)]
interface LocationComponent {

    @Component.Factory
    interface Factory {
        fun create(dependencies: LocationExternalDependencies): LocationComponent
    }

    fun inject(fragment: LocationListFragment)
    fun inject(fragment: LocationDetailsFragment)
    fun inject(fragment: LocationFeatureFlowFragment)
}