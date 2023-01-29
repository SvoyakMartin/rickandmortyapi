package ru.svoyakmartin.rickandmortyapi.data.db.models

import androidx.room.Entity
import ru.svoyakmartin.rickandmortyapi.CHARACTERS_LOCATIONS_TABLE_NAME
import java.io.Serializable

@Entity(tableName = CHARACTERS_LOCATIONS_TABLE_NAME, primaryKeys = ["characterId", "locationId"])
data class CharactersAndLocations(
    val characterId : Int,
    val locationId : Int
) : Serializable