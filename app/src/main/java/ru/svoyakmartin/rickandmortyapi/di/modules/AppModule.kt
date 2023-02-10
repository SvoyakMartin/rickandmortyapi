package ru.svoyakmartin.rickandmortyapi.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import ru.svoyakmartin.coreDi.di.scope.AppScope
import ru.svoyakmartin.featureCharacter.di.CharacterFeatureApiModule
import ru.svoyakmartin.featureHomeScreen.di.HomeScreenFeatureApiModule
import ru.svoyakmartin.featureLocation.data.dataSource.LocationsApi
import ru.svoyakmartin.featureLocation.data.db.LocationRoomDAO
import ru.svoyakmartin.featureLocation.data.db.LocationRoomDB
import ru.svoyakmartin.featureLocation.di.LocationFeatureApiModule
import ru.svoyakmartin.featureSettings.di.SettingsFeatureApiModule

@Module(includes = [
    HomeScreenFeatureApiModule::class,
    CharacterFeatureApiModule::class,
    SettingsFeatureApiModule::class,
    LocationFeatureApiModule::class
])
class AppModule {
    @[AppScope Provides]
    fun provideLocationRoomDAO(context: Context): LocationRoomDAO {
        return LocationRoomDB.getDatabase(context).getLocationDao()
    }

    @[AppScope Provides]
    fun provideLocationApi(retrofit: Retrofit): LocationsApi = retrofit.create()
}