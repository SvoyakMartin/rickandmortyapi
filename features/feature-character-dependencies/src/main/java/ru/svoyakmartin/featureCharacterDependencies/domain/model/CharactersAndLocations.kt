package ru.svoyakmartin.featureCharacterDependencies.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import ru.svoyakmartin.featureCharacterDependencies.CHARACTERS_LOCATIONS_TABLE_NAME
import java.io.Serializable

@Entity(tableName = CHARACTERS_LOCATIONS_TABLE_NAME, primaryKeys = ["characterId", "locationId"])
data class CharactersAndLocations(
    @ColumnInfo("characterId")
    val characterId : Int,
    @ColumnInfo("locationId")
    val locationId : Int
) : Serializable