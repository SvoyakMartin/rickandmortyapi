package ru.svoyakmartin.featureCharacterDependencies.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import ru.svoyakmartin.featureCharacterDependencies.CHARACTERS_EPISODES_TABLE_NAME
import java.io.Serializable

@Entity(tableName = CHARACTERS_EPISODES_TABLE_NAME, primaryKeys = ["characterId", "episodeId"])
data class CharactersAndEpisodes(
    @ColumnInfo("characterId")
    val characterId : Int,
    @ColumnInfo("episodeId")
    val episodeId : Int
) : Serializable