package ru.svoyakmartin.featureNotification.di

import dagger.Binds
import dagger.Module
import ru.svoyakmartin.featureNotification.NotificationFeatureApiImpl
import ru.svoyakmartin.featureNotificationApi.NotificationFeatureApi

@Module
interface NotificationFeatureApiModule {

    @Binds
    @Suppress("UNUSED")
    fun bindNotificationFeatureApi(api: NotificationFeatureApiImpl): NotificationFeatureApi
}