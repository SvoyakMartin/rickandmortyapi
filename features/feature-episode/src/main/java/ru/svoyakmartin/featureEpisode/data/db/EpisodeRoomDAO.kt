package ru.svoyakmartin.featureEpisode.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow
import ru.svoyakmartin.featureEpisode.EPISODES_TABLE_NAME
import ru.svoyakmartin.featureEpisode.domain.model.Episode
import ru.svoyakmartin.featureCore.domain.model.EntityMap

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

    @Query(
        "SELECT (episode || ' - ' || name) AS name, id \n" +
                "FROM $EPISODES_TABLE_NAME \n" +
                "WHERE id IN (:episodeIdsList)"
    )
    fun getEpisodesNameByIds(episodeIdsList: List<Int>): Flow<List<EntityMap>>

    @Query(
        "SELECT id \n" +
                "FROM $EPISODES_TABLE_NAME \n" +
                "WHERE id in (:episodeIdsList)"
    )
    fun getExistingEpisodeIds(episodeIdsList: List<Int>): Flow<List<Int>>
}