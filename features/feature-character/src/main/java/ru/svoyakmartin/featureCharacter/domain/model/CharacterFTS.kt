package ru.svoyakmartin.featureCharacter.domain.model

import androidx.room.Entity
import androidx.room.Fts4
import ru.svoyakmartin.featureCharacter.CHARACTERS_FTS_TABLE_NAME

@Fts4(contentEntity = Character::class)
@Entity(tableName = CHARACTERS_FTS_TABLE_NAME)
data class CharacterFTS(
    val id : Int,
    val name: String
)