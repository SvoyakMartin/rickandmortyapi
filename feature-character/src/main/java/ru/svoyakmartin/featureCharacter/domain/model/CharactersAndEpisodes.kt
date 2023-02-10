package ru.svoyakmartin.featureCharacter.domain.model

import androidx.room.Entity
import ru.svoyakmartin.featureCharacter.CHARACTERS_EPISODES_TABLE_NAME
import java.io.Serializable

@Entity(tableName = CHARACTERS_EPISODES_TABLE_NAME, primaryKeys = ["characterId", "episodeId"])
data class CharactersAndEpisodes(
    val characterId : Int,
    val episodeId : Int
) : Serializable