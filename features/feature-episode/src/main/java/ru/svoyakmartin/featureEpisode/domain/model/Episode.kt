package ru.svoyakmartin.featureEpisode.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.svoyakmartin.featureEpisode.EPISODES_TABLE_NAME
import java.io.Serializable

@Entity(tableName = EPISODES_TABLE_NAME)
data class Episode(
    @PrimaryKey
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String
) : Serializable