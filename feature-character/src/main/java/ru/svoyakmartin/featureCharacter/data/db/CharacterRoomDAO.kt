package ru.svoyakmartin.featureCharacter.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.svoyakmartin.featureCharacter.CHARACTERS_EPISODES_TABLE_NAME
import ru.svoyakmartin.featureCharacter.CHARACTERS_LOCATIONS_TABLE_NAME
import ru.svoyakmartin.featureCharacter.CHARACTERS_TABLE_NAME
import ru.svoyakmartin.featureCharacter.domain.model.Character
import ru.svoyakmartin.featureCharacter.domain.model.CharacterDependencies
import ru.svoyakmartin.featureCharacter.domain.model.CharactersAndEpisodes
import ru.svoyakmartin.featureCharacter.domain.model.CharactersAndLocations


@Dao
interface CharacterRoomDAO {
    @Query("SELECT * FROM $CHARACTERS_TABLE_NAME")
    fun getAllCharacters(): Flow<List<Character>?>

    @Query("SELECT * FROM $CHARACTERS_TABLE_NAME WHERE id = :id")
    fun getCharacterById(id: Int): Flow<Character?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: Character)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)

    @Transaction
    suspend fun insertCharactersAndDependencies(
        charactersList: List<Character>,
        charactersAndEpisodesIdsMap: Map<Int, List<Int>>
    ) {
        val charactersAndEpisodesList = arrayListOf<CharactersAndEpisodes>()

        charactersAndEpisodesIdsMap.forEach { (characterID, episodeIds) ->
            episodeIds.forEach { episodeId ->
                charactersAndEpisodesList.add(CharactersAndEpisodes(characterID, episodeId))
            }
        }

        insertCharacters(charactersList)
        insertCharactersAndEpisodes(charactersAndEpisodesList)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharactersAndEpisodes(charactersAndEpisodesList: List<CharactersAndEpisodes>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharactersAndLocations(charactersAndLocationsList: List<CharactersAndLocations>)

    @Query(
        "SELECT characterId \n" +
                "FROM $CHARACTERS_LOCATIONS_TABLE_NAME \n" +
                "LEFT JOIN $CHARACTERS_TABLE_NAME ON characterId = id \n" +
                "WHERE locationId = :locationId \n" +
                "AND id IS NULL"
    )
    fun getMissingCharacterIdsByLocationId(locationId: Int): Flow<List<Int>?>

    @Query(
        "SELECT characterId \n" +
                "FROM $CHARACTERS_EPISODES_TABLE_NAME \n" +
                "LEFT JOIN $CHARACTERS_TABLE_NAME ON characterId = id \n" +
                "WHERE episodeId = :episodeId \n" +
                "AND id IS NULL"
    )
    fun getMissingCharacterIdsByEpisodeId(episodeId: Int): Flow<List<Int>?>

    @Query(
        "SELECT name, id \n" +
                "FROM $CHARACTERS_TABLE_NAME \n" +
                "WHERE id IN ( \n" +
                "   SELECT characterId \n" +
                "   FROM $CHARACTERS_LOCATIONS_TABLE_NAME \n" +
                "   WHERE locationId = :locationId \n" +
                ")"
    )
    fun getCharactersByLocationId(locationId: Int): Flow<List<CharacterDependencies>?>

    @Query(
        "SELECT name, id \n" +
                "FROM $CHARACTERS_TABLE_NAME \n" +
                "WHERE id IN ( \n" +
                "   SELECT characterId \n" +
                "   FROM $CHARACTERS_EPISODES_TABLE_NAME \n" +
                "   WHERE episodeId = :episodeId \n" +
                ")"
    )
    fun getCharactersByEpisodeId(episodeId: Int): Flow<List<CharacterDependencies>?>
}