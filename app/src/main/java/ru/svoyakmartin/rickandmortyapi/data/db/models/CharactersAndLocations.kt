package ru.svoyakmartin.rickandmortyapi.data.db.models

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "characters_locations", primaryKeys = ["characterId", "locationId"])
data class CharactersAndLocations(
    val characterId : Int,
    val locationId : Int
) : Serializable