package ru.svoyakmartin.rickandmortyapi.data.db.models

import androidx.room.Entity
import ru.svoyakmartin.rickandmortyapi.CHARACTERS_EPISODES_TABLE_NAME
import java.io.Serializable

@Entity(tableName = CHARACTERS_EPISODES_TABLE_NAME, primaryKeys = ["characterId", "episodeId"])
data class CharactersAndEpisodes(
    val characterId : Int,
    val episodeId : Int
) : Serializable