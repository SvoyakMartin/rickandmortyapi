package ru.svoyakmartin.featureEpisode.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.svoyakmartin.featureEpisode.EPISODES_TABLE_NAME
import ru.svoyakmartin.featureEpisode.domain.model.Episode


@Dao
interface EpisodeRoomDAO {
    @Query("SELECT * FROM $EPISODES_TABLE_NAME")
    fun getAllEpisodes(): Flow<List<Episode>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<Episode>)

//    @Transaction
//    suspend fun insertCharactersAndDependencies(
//        charactersList: List<Character>,
//        charactersAndEpisodesIdsMap: Map<Int, List<Int>>
//    ) {
//        val charactersAndEpisodesList = arrayListOf<CharactersAndEpisodes>()
//
//        charactersAndEpisodesIdsMap.forEach { (characterID, episodeIds) ->
//            episodeIds.forEach { episodeId ->
//                charactersAndEpisodesList.add(CharactersAndEpisodes(characterID, episodeId))
//            }
//        }
//
////        insertCharacters(charactersList)
//        insertCharactersAndEpisodes(charactersAndEpisodesList)
//    }
//
//    @Transaction
//    suspend fun insertEpisodesAndDependencies(
//        episodesList: List<Episode>,
//        charactersAndEpisodesIdsMap: Map<Int, List<Int>>
//    ) {
//        val charactersAndEpisodesList = arrayListOf<CharactersAndEpisodes>()
//
//        charactersAndEpisodesIdsMap.forEach { (episodeID, charactersIds) ->
//            charactersIds.forEach { characterID ->
//                charactersAndEpisodesList.add(CharactersAndEpisodes(characterID, episodeID))
//            }
//        }
//
//        insertEpisodes(episodesList)
//        insertCharactersAndEpisodes(charactersAndEpisodesList)
//    }
//
//    @Transaction
//    suspend fun insertLocationsAndDependencies(
//        locationsList: List<ru.svoyakmartin.featureLocation.domain.model.Location>,
//        charactersAndLocationsIdsMap: Map<Int, List<Int>>
//    ) {
//        val charactersAndLocationsList = arrayListOf<CharactersAndLocations>()
//
//        charactersAndLocationsIdsMap.forEach { (locationID, charactersIds) ->
//            charactersIds.forEach { characterID ->
//                charactersAndLocationsList.add(CharactersAndLocations(characterID, locationID))
//            }
//        }
//
////        insertLocations(locationsList)
//        insertCharactersAndLocations(charactersAndLocationsList)
//    }
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertCharactersAndEpisodes(charactersEpisodes: List<CharactersAndEpisodes>)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertCharactersAndLocations(charactersLocations: List<CharactersAndLocations>)

//    @Query(
//        "SELECT * \n" +
//                "FROM $CHARACTERS_TABLE_NAME \n" +
//                "WHERE id IN ( \n" +
//                "   SELECT characterId \n" +
//                "   FROM $CHARACTERS_LOCATIONS_TABLE_NAME \n" +
//                "   WHERE locationId = :locationId \n" +
//                ")"
//    )
//    fun getCharactersFromLocationId(locationId: Int): Flow<List<ru.svoyakmartin.featureCharacter.domain.model.Character>>

//    @Query(
//        "SELECT * \n" +
//                "FROM $CHARACTERS_TABLE_NAME \n" +
//                "WHERE id IN ( \n" +
//                "   SELECT characterId \n" +
//                "   FROM $CHARACTERS_EPISODES_TABLE_NAME \n" +
//                "   WHERE episodeId = :episodeId \n" +
//                ")"
//    )
//    fun getCharactersFromEpisodeId(episodeId: Int): Flow<List<ru.svoyakmartin.featureCharacter.domain.model.Character>>

//    @Query(
//        "SELECT * \n" +
//                "FROM $LOCATIONS_TABLE_NAME \n" +
//                "WHERE id IN ( \n" +
//                "   SELECT locationId \n" +
//                "   FROM $CHARACTERS_LOCATIONS_TABLE_NAME \n" +
//                "   WHERE characterId = :characterId \n" +
//                ")"
//    )
//    fun getLocationsFromCharacterId(characterId: Int): Flow<List<ru.svoyakmartin.featureLocation.domain.model.Location>>

//    @Query(
//        "SELECT * \n" +
//                "FROM $EPISODES_TABLE_NAME \n" +
//                "WHERE id IN ( \n" +
//                "   SELECT episodeId \n" +
//                "   FROM $CHARACTERS_EPISODES_TABLE_NAME \n" +
//                "   WHERE characterId = :characterId \n" +
//                ")"
//    )
//    fun getEpisodesByCharacterId(characterId: Int): Flow<List<Episode>?>

//    @Query(
//        "SELECT * \n" +
//                "FROM $CHARACTERS_TABLE_NAME \n" +
//                "WHERE id IN ( \n" +
//                "   SELECT characterId \n" +
//                "   FROM $CHARACTERS_EPISODES_TABLE_NAME \n" +
//                "   WHERE episodeId = :episodeId \n" +
//                ")"
//    )
//    fun getCharactersByEpisodeId(episodeId: Int): Flow<List<ru.svoyakmartin.featureCharacter.domain.model.Character>?>

//    @Query(
//        "SELECT * \n" +
//                "FROM $CHARACTERS_TABLE_NAME \n" +
//                "WHERE id IN ( \n" +
//                "   SELECT characterId \n" +
//                "   FROM $CHARACTERS_LOCATIONS_TABLE_NAME \n" +
//                "   WHERE locationId = :locationId \n" +
//                ")"
//    )
//    fun getCharactersByLocationId(locationId: Int): Flow<List<ru.svoyakmartin.featureCharacter.domain.model.Character>?>

//    @Query(
//        "SELECT episodeId \n" +
//                "FROM $CHARACTERS_EPISODES_TABLE_NAME \n" +
//                "LEFT JOIN $EPISODES_TABLE_NAME ON episodeId = id \n" +
//                "WHERE characterId = :characterId \n" +
//                "AND id IS NULL"
//    )
//    fun getMissingEpisodeIdsByCharacterId(characterId: Int): Flow<List<Int>?>

//    @Query(
//        "SELECT characterId \n" +
//                "FROM $CHARACTERS_EPISODES_TABLE_NAME \n" +
//                "LEFT JOIN $CHARACTERS_TABLE_NAME ON characterId = id \n" +
//                "WHERE episodeId = :episodeId \n" +
//                "AND id IS NULL"
//    )
//    fun getMissingCharacterIdsByEpisodeId(episodeId: Int): Flow<List<Int>?>
//
//    @Query(
//        "SELECT characterId \n" +
//                "FROM $CHARACTERS_LOCATIONS_TABLE_NAME \n" +
//                "LEFT JOIN $CHARACTERS_TABLE_NAME ON characterId = id \n" +
//                "WHERE locationId = :locationId \n" +
//                "AND id IS NULL"
//    )
//    fun getMissingCharacterIdsByLocationId(locationId: Int): Flow<List<Int>?>
}