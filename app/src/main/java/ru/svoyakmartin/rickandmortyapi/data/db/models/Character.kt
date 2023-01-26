package ru.svoyakmartin.rickandmortyapi.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.svoyakmartin.rickandmortyapi.data.repository.Gender
import ru.svoyakmartin.rickandmortyapi.data.repository.Status
import java.io.Serializable

@Entity(tableName = "characters")
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