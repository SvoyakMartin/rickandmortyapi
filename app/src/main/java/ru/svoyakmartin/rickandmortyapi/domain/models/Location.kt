package ru.svoyakmartin.rickandmortyapi.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class Location(
    @PrimaryKey
    val id: Int,// The id of the location.
    val name: String,// The name of the location.
    val type: String,// The type of the location.
    val dimension: String,// The dimension in which the location is located.
//    val residents: List<String>,// List of character who have been last seen in the location.
    val url: String,// Link to the location's own endpoint.
    val created: String// Time at which the location was created in the database.
)