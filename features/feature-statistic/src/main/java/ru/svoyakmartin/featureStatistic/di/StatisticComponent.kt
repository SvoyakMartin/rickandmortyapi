package ru.svoyakmartin.featureStatistic.di

import dagger.Component
import ru.svoyakmartin.coreDi.di.scope.FeatureScope


@[FeatureScope Component(
    modules = [
        StatisticFeatureApiModule::class,
        StatisticProvidesModule::class],
    dependencies = [StatisticExternalDependencies::class]
)]
interface StatisticComponent {
    @Component.Factory
    interface Factory {
        fun create(dependencies: StatisticExternalDependencies): StatisticComponent
    }
}