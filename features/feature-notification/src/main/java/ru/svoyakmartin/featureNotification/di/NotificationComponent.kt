package ru.svoyakmartin.featureNotification.di

import dagger.Component
import ru.svoyakmartin.coreDi.di.scope.FeatureScope

@[FeatureScope Component(
    modules = [NotificationFeatureApiModule::class],
    dependencies = [NotificationExternalDependencies::class]
)]
interface NotificationComponent {

    @Component.Factory
    interface Factory {
        fun create(dependencies: NotificationExternalDependencies): NotificationComponent
    }
}