package ru.svoyakmartin.featureCharacter.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow
import ru.svoyakmartin.featureCharacter.CHARACTERS_FTS_TABLE_NAME
import ru.svoyakmartin.featureCharacter.CHARACTERS_TABLE_NAME
import ru.svoyakmartin.featureCharacter.domain.model.Character
import ru.svoyakmartin.featureCore.domain.model.EntityMap

@Dao
interface CharacterRoomDAO {
    @Query("SELECT * FROM $CHARACTERS_TABLE_NAME")
    fun getAllCharacters(): Flow<List<Character>>

    @Query("SELECT $CHARACTERS_TABLE_NAME.*" +
            "FROM $CHARACTERS_TABLE_NAME " +
            "JOIN $CHARACTERS_FTS_TABLE_NAME " +
            "ON $CHARACTERS_TABLE_NAME.id = $CHARACTERS_FTS_TABLE_NAME.id " +
            "WHERE $CHARACTERS_FTS_TABLE_NAME.name MATCH :search")
    fun getFilteredCharacters(search: String): Flow<List<Character>>

    @Query("SELECT * FROM $CHARACTERS_TABLE_NAME WHERE id = :id")
    fun getCharacterById(id: Int): Flow<Character?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: Character)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)

    @Query(
        "SELECT id \n" +
                "FROM $CHARACTERS_TABLE_NAME \n" +
                "WHERE id in (:characterIdsList)"
    )
    fun getExistingCharacterIds(characterIdsList: Set<Int>): Flow<List<Int>>

    @Query(
        "SELECT id, name \n" +
                "FROM $CHARACTERS_TABLE_NAME \n" +
                "WHERE id in (:characterIdsList)"
    )
    fun getCharactersNameByIds(characterIdsList: Set<Int>): Flow<List<EntityMap>>
}