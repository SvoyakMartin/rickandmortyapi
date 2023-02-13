package ru.svoyakmartin.featureLocation.di

import dagger.Component
import ru.svoyakmartin.coreDi.di.scope.FeatureScope
import ru.svoyakmartin.featureLocation.ui.fragment.LocationDetailsFragment
import ru.svoyakmartin.featureLocation.ui.fragment.LocationListFragment

@[FeatureScope Component(
    modules = [
        LocationFeatureApiModule::class,
        LocationProvidesModule::class,
        LocationBindsModule::class],
    dependencies = [LocationExternalDependencies::class]
)]
interface LocationComponent {

    @Component.Factory
    interface Factory {
        fun create(dependencies: LocationExternalDependencies): LocationComponent
    }

    fun inject(fragment: LocationListFragment)
    fun inject(fragment: LocationDetailsFragment)
}