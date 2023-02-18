package ru.svoyakmartin.featureCharacterDependencies.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.svoyakmartin.featureCharacterDependencies.data.db.CharacterDependenciesRoomDAO
import ru.svoyakmartin.featureCharacterDependencies.data.db.CharacterDependenciesRoomDB

@Module
object CharacterDependenciesProvidesModule {
    @Provides
    fun provideCharacterDependenciesRoomDAO(context: Context): CharacterDependenciesRoomDAO {
        return CharacterDependenciesRoomDB.getDatabase(context).getCharacterDependenciesDao()
    }
}