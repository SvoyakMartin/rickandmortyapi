package ru.svoyakmartin.featureCharacter.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.svoyakmartin.featureCharacter.CHARACTERS_TABLE_NAME
import java.io.Serializable

@Entity(tableName = CHARACTERS_TABLE_NAME)
data class Character(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("status")
    val status: Status,
    @ColumnInfo("species")
    val species: String,
    @ColumnInfo("type")
    val type: String,
    @ColumnInfo("gender")
    val gender: Gender,
    @ColumnInfo("origin")
    val origin: Int?,
    @ColumnInfo("location")
    val location: Int?,
    @ColumnInfo("image")
    val image: String
) : Serializable {

    fun toMap(): Map<String, Any> = mapOf(
        "id" to id,
        "name" to name,
        "image" to image,
        "species" to species,
        "type" to type
    )
}