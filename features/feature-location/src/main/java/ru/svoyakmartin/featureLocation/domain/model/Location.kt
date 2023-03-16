package ru.svoyakmartin.featureLocation.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.svoyakmartin.featureLocation.LOCATIONS_TABLE_NAME
import java.io.Serializable

@Entity(tableName = LOCATIONS_TABLE_NAME)
data class Location(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("type")
    val type: String,
    @ColumnInfo("dimension")
    val dimension: String
) : Serializable