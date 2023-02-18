package ru.svoyakmartin.featureCharacterDependencies.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow
import ru.svoyakmartin.featureCharacterDependencies.CHARACTERS_EPISODES_TABLE_NAME
import ru.svoyakmartin.featureCharacterDependencies.CHARACTERS_LOCATIONS_TABLE_NAME
import ru.svoyakmartin.featureCharacterDependencies.domain.model.CharactersAndEpisodes
import ru.svoyakmartin.featureCharacterDependencies.domain.model.CharactersAndLocations

@Dao
interface CharacterDependenciesRoomDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharactersAndEpisodes(charactersAndEpisodesList: List<CharactersAndEpisodes>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharactersAndLocations(charactersAndLocationsList: List<CharactersAndLocations>)

    @Query(
        "SELECT characterId \n" +
                "FROM $CHARACTERS_EPISODES_TABLE_NAME \n" +
                "WHERE episodeId = :episodeId"
    )
    fun getCharactersIdsByEpisodeId(episodeId: Int): Flow<List<Int>>

    @Query(
        "SELECT characterId \n" +
                "FROM $CHARACTERS_LOCATIONS_TABLE_NAME \n" +
                "WHERE locationId = :locationId"
    )
    fun getCharactersIdsByLocationId(locationId: Int): Flow<List<Int>>

    @Query(
        "SELECT episodeId \n" +
                "FROM $CHARACTERS_EPISODES_TABLE_NAME \n" +
                "WHERE characterId = :characterId"
    )
    fun getEpisodesIdsByCharacterId(characterId: Int): Flow<List<Int>>
}