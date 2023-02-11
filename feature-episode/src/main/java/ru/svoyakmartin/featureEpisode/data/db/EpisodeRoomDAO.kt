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
    suspend fun insertEpisodes(episodeList: List<Episode>)

    @Query("SELECT * FROM $EPISODES_TABLE_NAME WHERE id = :id")
    fun getEpisodeById(id: Int): Flow<Episode?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(character: Episode)

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
}