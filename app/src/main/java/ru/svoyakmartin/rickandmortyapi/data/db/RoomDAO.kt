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
    fun getAllCharacters(): Flow<List<Character>>

    @Query("SELECT * FROM locations")
    fun getAllLocations(): Flow<List<Location>>

    @Query("SELECT * FROM episodes")
    fun getAllEpisodes(): Flow<List<Episode>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: Character)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)

    @Transaction
    suspend fun insertCharactersAndDependencies(
        characters: List<Character>,
        charactersEpisodes: Map<Int, List<Int>>
    ) {
        val charactersEpisodesList = arrayListOf<CharactersAndEpisodes>()

        charactersEpisodes.forEach { (characterID, episodeIds) ->
            episodeIds.forEach {episodeId ->
                charactersEpisodesList.add(CharactersAndEpisodes(characterID, episodeId))
            }
        }

        insertCharacters(characters)
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
                "   WHERE locationId= :locationId" +
                ")"
    )
    fun getCharactersFromLocation(locationId: Int): Flow<List<Character>>

    @Query(
        "SELECT * " +
                "FROM characters " +
                "WHERE id IN (" +
                "   SELECT characterId " +
                "   FROM characters_episodes " +
                "   WHERE episodeId= :episodeId" +
                ")"
    )
    fun getCharactersFromEpisode(episodeId: Int): Flow<List<Character>>

    @Query(
        "SELECT * " +
                "FROM locations " +
                "WHERE id IN (" +
                "   SELECT locationId " +
                "   FROM characters_locations " +
                "   WHERE characterId= :characterId" +
                ")"
    )
    fun getLocationsFromCharacter(characterId: Int): Flow<List<Location>>

    @Query(
        "SELECT * " +
                "FROM episodes " +
                "WHERE id IN (" +
                "   SELECT episodeId " +
                "   FROM characters_episodes " +
                "   WHERE characterId= :characterId" +
                ")"
    )
    fun getEpisodesFromCharacter(characterId: Int): Flow<List<Episode>>
}