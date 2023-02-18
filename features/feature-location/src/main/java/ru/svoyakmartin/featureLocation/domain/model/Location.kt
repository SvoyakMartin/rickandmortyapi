package ru.svoyakmartin.featureLocation.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.svoyakmartin.featureLocation.LOCATIONS_TABLE_NAME
import java.io.Serializable

@Entity(tableName = LOCATIONS_TABLE_NAME)
data class Location(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
) : Serializable