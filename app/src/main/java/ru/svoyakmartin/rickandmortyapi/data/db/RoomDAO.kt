package ru.svoyakmartin.rickandmortyapi.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.svoyakmartin.rickandmortyapi.*
import ru.svoyakmartin.rickandmortyapi.data.db.models.*

@Dao
interface RoomDAO {
    @Query("SELECT * FROM characters")
    fun getAllCharacters(): Flow<List<Character>?>

    @Query("SELECT * FROM locations")
    fun getAllLocations(): Flow<List<Location>?>

    @Query("SELECT * FROM episodes")
    fun getAllEpisodes(): Flow<List<Episode>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<Episode>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(episodes: List<Location>)

    @Transaction
    suspend fun insertCharactersAndDependencies(
        charactersList: List<Character>,
        charactersAndEpisodesIdsMap: Map<Int, List<Int>>
    ) {
        val charactersAndEpisodesList = arrayListOf<CharactersAndEpisodes>()

        charactersAndEpisodesIdsMap.forEach { (characterID, episodeIds) ->
            episodeIds.forEach { episodeId ->
                charactersAndEpisodesList.add(CharactersAndEpisodes(characterID, episodeId))
            }
        }

        insertCharacters(charactersList)
        insertCharactersAndEpisodes(charactersAndEpisodesList)
    }

    @Transaction
    suspend fun insertEpisodesAndDependencies(
        episodesList: List<Episode>,
        charactersAndEpisodesIdsMap: Map<Int, List<Int>>
    ) {
        val charactersAndEpisodesList = arrayListOf<CharactersAndEpisodes>()

        charactersAndEpisodesIdsMap.forEach { (episodeID, charactersIds) ->
            charactersIds.forEach { characterID ->
                charactersAndEpisodesList.add(CharactersAndEpisodes(characterID, episodeID))
            }
        }

        insertEpisodes(episodesList)
        insertCharactersAndEpisodes(charactersAndEpisodesList)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharactersAndEpisodes(charactersEpisodes: List<CharactersAndEpisodes>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharactersAndLocation(charactersEpisodes: List<CharactersAndLocations>)

    @Query(
        "SELECT * \n" +
                "FROM $CHARACTERS_TABLE_NAME \n" +
                "WHERE id IN ( \n" +
                "   SELECT characterId \n" +
                "   FROM $CHARACTERS_LOCATIONS_TABLE_NAME \n" +
                "   WHERE locationId = :locationId \n" +
                ")"
    )
    fun getCharactersFromLocationId(locationId: Int): Flow<List<Character>>

    @Query(
        "SELECT * \n" +
                "FROM $CHARACTERS_TABLE_NAME \n" +
                "WHERE id IN ( \n" +
                "   SELECT characterId \n" +
                "   FROM $CHARACTERS_EPISODES_TABLE_NAME \n" +
                "   WHERE episodeId = :episodeId \n" +
                ")"
    )
    fun getCharactersFromEpisodeId(episodeId: Int): Flow<List<Character>>

    @Query(
        "SELECT * \n" +
                "FROM $LOCATIONS_TABLE_NAME \n" +
                "WHERE id IN ( \n" +
                "   SELECT locationId \n" +
                "   FROM $CHARACTERS_LOCATIONS_TABLE_NAME \n" +
                "   WHERE characterId = :characterId \n" +
                ")"
    )
    fun getLocationsFromCharacterId(characterId: Int): Flow<List<Location>>

    @Query(
        "SELECT * \n" +
                "FROM $EPISODES_TABLE_NAME \n" +
                "WHERE id IN ( \n" +
                "   SELECT episodeId \n" +
                "   FROM $CHARACTERS_EPISODES_TABLE_NAME \n" +
                "   WHERE characterId = :characterId \n" +
                ")"
    )
    fun getEpisodesByCharactersId(characterId: Int): Flow<List<Episode>?>

    @Query(
        "SELECT episodeId \n" +
                "FROM $CHARACTERS_EPISODES_TABLE_NAME \n" +
                "LEFT JOIN $EPISODES_TABLE_NAME ON episodeId = id \n" +
                "WHERE characterId = :characterId \n" +
                "AND id IS NULL"
    )
    fun getMissingEpisodeIdsByCharacterId(characterId: Int): Flow<List<Int>?>

    @Query("SELECT * FROM $LOCATIONS_TABLE_NAME WHERE id = :locationId")
    fun getLocationById(locationId: Int): Flow<Location>
}