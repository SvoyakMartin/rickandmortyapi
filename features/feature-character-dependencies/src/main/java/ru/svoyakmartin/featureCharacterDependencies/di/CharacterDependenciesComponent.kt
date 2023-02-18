package ru.svoyakmartin.featureCharacterDependencies.di

import dagger.Component
import ru.svoyakmartin.coreDi.di.scope.FeatureScope

@[FeatureScope Component(
    modules = [
        CharacterDependenciesFeatureApiModule::class,
        CharacterDependenciesProvidesModule::class],
    dependencies = [CharacterDependenciesExternalDependencies::class]
)]
interface CharacterDependenciesComponent {

    @Component.Factory
    interface Factory {
        fun create(dependencies: CharacterDependenciesExternalDependencies): CharacterDependenciesComponent
    }
}