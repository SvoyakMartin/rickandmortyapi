package ru.svoyakmartin.featureHomeScreen.di

import dagger.Component
import ru.svoyakmartin.coreDi.di.scope.FeatureScope
import ru.svoyakmartin.featureHomeScreen.ui.fragment.HomeScreenFragment

@[FeatureScope Component(
    modules = [HomeScreenFeatureApiModule::class],
    dependencies = [HomeScreenExternalDependencies::class]
)]
interface HomeScreenComponent {
    @Component.Factory
    interface Factory {
        fun create(dependencies: HomeScreenExternalDependencies): HomeScreenComponent
    }

    fun inject(fragment: HomeScreenFragment)
}