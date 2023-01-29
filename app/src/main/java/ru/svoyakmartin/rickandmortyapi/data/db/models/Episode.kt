package ru.svoyakmartin.rickandmortyapi.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.svoyakmartin.rickandmortyapi.EPISODES_TABLE_NAME
import java.io.Serializable

@Entity(tableName = EPISODES_TABLE_NAME)
data class Episode(
    @PrimaryKey
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val url: String,
    val created: Long
) : Serializable