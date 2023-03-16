package ru.svoyakmartin.featureEpisode.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.svoyakmartin.featureEpisode.EPISODES_TABLE_NAME
import java.io.Serializable

@Entity(tableName = EPISODES_TABLE_NAME)
data class Episode(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("airDate")
    val airDate: String,
    @ColumnInfo("episode")
    val episode: String
) : Serializable