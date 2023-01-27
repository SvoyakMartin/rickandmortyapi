package ru.svoyakmartin.rickandmortyapi.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.svoyakmartin.rickandmortyapi.data.db.models.Character
import ru.svoyakmartin.rickandmortyapi.data.db.models.CharactersAndEpisodes
import ru.svoyakmartin.rickandmortyapi.data.db.models.Episode
import ru.svoyakmartin.rickandmortyapi.data.db.models.Location

@Dao
interface RoomDAO {
    @Query("SELECT * FROM characters")
    fun getAllCharacters(): Flow<List<Character>?>

    @Query("SELECT * FROM locations")
    fun getAllLocations(): Flow<List<Location>?>

    @Query("SELECT * FROM episodes")
    fun getAllEpisodes(): Flow<List<Episode>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: Character)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<Episode>)

    @Transaction
    suspend fun insertCharactersAndDependencies(
        characters: List<Character>,
        charactersEpisodes: Map<Int, List<Int>>
    ) {
        val charactersEpisodesList = arrayListOf<CharactersAndEpisodes>()

        charactersEpisodes.forEach { (characterID, episodeIds) ->
            episodeIds.forEach { episodeId ->
                charactersEpisodesList.add(CharactersAndEpisodes(characterID, episodeId))
            }
        }

        insertCharacters(characters)
        insertCharactersAndEpisodes(charactersEpisodesList)
    }

    @Transaction
    suspend fun insertEpisodesAndDependencies(
        episodes: List<Episode>,
        charactersEpisodes: Map<Int, List<Int>>
    ) {
        val charactersEpisodesList = arrayListOf<CharactersAndEpisodes>()

        charactersEpisodes.forEach { (episodeID, charactersIds) ->
            charactersIds.forEach { characterID ->
                charactersEpisodesList.add(CharactersAndEpisodes(characterID, episodeID))
            }
        }

        insertEpisodes(episodes)
        insertCharactersAndEpisodes(charactersEpisodesList)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharactersAndEpisodes(charactersEpisodes: List<CharactersAndEpisodes>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(episode: Episode)

    @Query(
        "SELECT * " +
                "FROM characters " +
                "WHERE id IN (" +
                "   SELECT characterId " +
                "   FROM characters_locations " +
                "   WHERE locationId = :locationId" +
                ")"
    )
    fun getCharactersFromLocation(locationId: Int): Flow<List<Character>>

    @Query(
        "SELECT * " +
                "FROM characters " +
                "WHERE id IN (" +
                "   SELECT characterId " +
                "   FROM characters_episodes " +
                "   WHERE episodeId = :episodeId" +
                ")"
    )
    fun getCharactersFromEpisode(episodeId: Int): Flow<List<Character>>

    @Query(
        "SELECT * " +
                "FROM locations " +
                "WHERE id IN (" +
                "   SELECT locationId " +
                "   FROM characters_locations " +
                "   WHERE characterId = :characterId" +
                ")"
    )
    fun getLocationsFromCharacter(characterId: Int): Flow<List<Location>>

    @Query(
        "SELECT * " +
                "FROM episodes " +
                "WHERE id IN (" +
                "   SELECT episodeId " +
                "   FROM characters_episodes " +
                "   WHERE characterId = :characterId" +
                ")"
    )
    fun getEpisodesByCharactersId(characterId: Int): Flow<List<Episode>?>

    @Query("SELECT episodeId AS id \n" +
            "FROM characters_episodes \n" +
            "LEFT JOIN episodes ON episodeId = id \n" +
            "WHERE characterId = :characterId \n" +
            "AND id IS NULL")
    fun getMissingEpisodeIdsByCharacterId(characterId: Int): Flow<List<Int>?>

    @Query("SELECT * FROM locations WHERE id = :id")
    fun getLocation(id: Int): Flow<Location>
}