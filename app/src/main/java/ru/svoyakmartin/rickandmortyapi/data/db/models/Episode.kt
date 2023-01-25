package ru.svoyakmartin.rickandmortyapi.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "episodes")
data class Episode(
    @PrimaryKey
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val url: String,
    val created: Long
) : Serializable