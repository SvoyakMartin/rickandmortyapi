package ru.svoyakmartin.featureSettings.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.svoyakmartin.featureSettings.data.UserPreferencesRepositoryImpl

@Module
class SettingsModule {
    @Provides
    fun provideUserPreferencesRepository(context: Context): UserPreferencesRepositoryImpl {
        return UserPreferencesRepositoryImpl.getInstance(context)
    }
}