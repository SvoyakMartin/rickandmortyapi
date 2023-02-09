package ru.svoyakmartin.featureCharacter.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.svoyakmartin.featureCharacter.CHARACTERS_TABLE_NAME
import java.io.Serializable

@Entity(tableName = CHARACTERS_TABLE_NAME)
data class Character(
    @PrimaryKey
    val id : Int,
    val name: String,
    val status: Status,
    val species: String,
    val type: String,
    val gender: Gender,
    val origin: Int?,
    val location: Int?,
    val image: String,
    val url: String,
    val created: Long
) : Serializable