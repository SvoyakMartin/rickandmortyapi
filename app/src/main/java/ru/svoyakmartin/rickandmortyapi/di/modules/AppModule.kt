package ru.svoyakmartin.rickandmortyapi.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import ru.svoyakmartin.coreDi.di.scope.AppScope
import ru.svoyakmartin.featureCharacter.data.dataSource.CharactersApi
import ru.svoyakmartin.featureCharacter.data.db.CharacterRoomDAO
import ru.svoyakmartin.featureCharacter.data.db.CharacterRoomDB
import ru.svoyakmartin.featureCharacter.di.CharacterFeatureApiModule
import ru.svoyakmartin.featureCharacterDependencies.data.db.CharacterDependenciesRoomDAO
import ru.svoyakmartin.featureCharacterDependencies.data.db.CharacterDependenciesRoomDB
import ru.svoyakmartin.featureCharacterDependencies.di.CharacterDependenciesFeatureApiModule
import ru.svoyakmartin.featureEpisode.data.dataSource.EpisodesApi
import ru.svoyakmartin.featureEpisode.data.db.EpisodeRoomDAO
import ru.svoyakmartin.featureEpisode.data.db.EpisodeRoomDB
import ru.svoyakmartin.featureEpisode.di.EpisodeFeatureApiModule
import ru.svoyakmartin.featureHomeScreen.di.HomeScreenFeatureApiModule
import ru.svoyakmartin.featureLocation.data.dataSource.LocationsApi
import ru.svoyakmartin.featureLocation.data.db.LocationRoomDAO
import ru.svoyakmartin.featureLocation.data.db.LocationRoomDB
import ru.svoyakmartin.featureLocation.di.LocationFeatureApiModule
import ru.svoyakmartin.featureSettings.di.SettingsFeatureApiModule
import ru.svoyakmartin.featureStatistic.data.dataSource.StatisticApi
import ru.svoyakmartin.featureStatistic.di.StatisticFeatureApiModule

@Module(
    includes = [
        HomeScreenFeatureApiModule::class,
        CharacterFeatureApiModule::class,
        CharacterDependenciesFeatureApiModule::class,
        SettingsFeatureApiModule::class,
        LocationFeatureApiModule::class,
        EpisodeFeatureApiModule::class,
        StatisticFeatureApiModule::class
    ]
)
object AppModule {
    @[AppScope Provides]
    fun provideCharacterDependenciesRoomDAO(context: Context): CharacterDependenciesRoomDAO =
        CharacterDependenciesRoomDB.getDatabase(context).getCharacterDependenciesDao()

    @[AppScope Provides]
    fun provideCharacterRoomDAO(context: Context): CharacterRoomDAO =
        CharacterRoomDB.getDatabase(context).getCharacterDao()

    @[AppScope Provides]
    fun provideCharacterApi(retrofit: Retrofit): CharactersApi = retrofit.create()

    @[AppScope Provides]
    fun provideLocationRoomDAO(context: Context): LocationRoomDAO =
        LocationRoomDB.getDatabase(context).getLocationDao()

    @[AppScope Provides]
    fun provideLocationApi(retrofit: Retrofit): LocationsApi = retrofit.create()

    @[AppScope Provides]
    fun provideEpisodeRoomDAO(context: Context): EpisodeRoomDAO =
        EpisodeRoomDB.getDatabase(context).getEpisodeDao()

    @[AppScope Provides]
    fun provideEpisodeApi(retrofit: Retrofit): EpisodesApi = retrofit.create()

    @[AppScope Provides]
    fun provideStatisticApi(retrofit: Retrofit): StatisticApi = retrofit.create()
}