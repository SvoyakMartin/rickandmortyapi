package ru.svoyakmartin.featureCharacter.di

import dagger.Component
import ru.svoyakmartin.coreDi.di.scope.FeatureScope
import ru.svoyakmartin.coreFlow.di.FlowModule
import ru.svoyakmartin.featureCharacter.ui.fragment.CharacterDetailsFragment
import ru.svoyakmartin.featureCharacter.ui.fragment.CharacterFeatureFlowFragment
import ru.svoyakmartin.featureCharacter.ui.fragment.CharacterListFragment

@[FeatureScope Component(
    modules = [
        CharacterFeatureApiModule::class,
        CharacterProvidesModule::class,
        CharacterBindsModule::class,
        FlowModule::class],
    dependencies = [CharacterExternalDependencies::class]
)]
interface CharacterComponent {

    @Component.Factory
    interface Factory {
        fun create(dependencies: CharacterExternalDependencies): CharacterComponent
    }

    fun inject(fragment: CharacterListFragment)
    fun inject(fragment: CharacterDetailsFragment)
    fun inject(fragment: CharacterFeatureFlowFragment)
}