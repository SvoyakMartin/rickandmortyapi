package ru.svoyakmartin.featureCharacter.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.svoyakmartin.featureCharacter.CHARACTERS_TABLE_NAME
import java.io.Serializable

@Entity(tableName = CHARACTERS_TABLE_NAME)
data class Character(
    @PrimaryKey
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val type: String,
    val gender: Gender,
    val origin: Int?,
    val location: Int?,
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