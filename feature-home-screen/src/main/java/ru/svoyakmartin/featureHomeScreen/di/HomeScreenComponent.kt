package ru.svoyakmartin.featureHomeScreen.di

import dagger.Binds
import dagger.Component
import dagger.multibindings.IntoMap
import ru.svoyakmartin.coreDi.di.dependency.FeatureExternalDependencies
import ru.svoyakmartin.coreDi.di.dependency.FeatureExternalDependenciesKey
import ru.svoyakmartin.coreDi.di.scope.FeatureScope
import ru.svoyakmartin.coreFlow.di.FlowModule
import ru.svoyakmartin.featureHomeScreen.ui.fragment.HomeScreenFeatureFlowFragment
import ru.svoyakmartin.featureHomeScreen.ui.fragment.HomeScreenFragment

@[FeatureScope Component(
    modules = [HomeScreenFeatureApiModule::class, FlowModule::class],
    dependencies = [HomeScreenExternalDependencies::class]
)]
interface HomeScreenComponent {
    @Component.Factory
    interface Factory {
        fun create(dependencies: HomeScreenExternalDependencies): HomeScreenComponent
    }

    fun inject(fragment: HomeScreenFragment)
    fun inject(fragment: HomeScreenFeatureFlowFragment)
}