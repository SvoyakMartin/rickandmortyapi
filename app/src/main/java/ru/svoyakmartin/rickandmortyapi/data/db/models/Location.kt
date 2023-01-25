package ru.svoyakmartin.rickandmortyapi.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "locations")
data class Location(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val url: String,
    val created: Long
) : Serializable