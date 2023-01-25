package ru.svoyakmartin.rickandmortyapi.data.db.models

import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "characters_episodes", primaryKeys = ["characterId", "episodeId"])
data class CharactersAndEpisodes(
    val characterId : Int,
    val episodeId : Int
) : Serializable