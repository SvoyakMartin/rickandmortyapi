package ru.svoyakmartin.featureSettings.di

import dagger.Component
import ru.svoyakmartin.coreDi.di.scope.FeatureScope
import ru.svoyakmartin.coreFlow.di.FlowModule
import ru.svoyakmartin.featureSettings.ui.fragment.SettingsFeatureFlowFragment
import ru.svoyakmartin.featureSettings.ui.fragment.SettingsFragment

@[FeatureScope Component(
    modules = [
        SettingsFeatureApiModule::class,
        FlowModule::class],
    dependencies = [SettingsExternalDependencies::class]
)]
interface SettingsComponent {
    @Component.Factory
    interface Factory {
        fun create(dependencies: SettingsExternalDependencies): SettingsComponent
    }

    fun inject(fragment: SettingsFragment)
    fun inject(fragment: SettingsFeatureFlowFragment)
}