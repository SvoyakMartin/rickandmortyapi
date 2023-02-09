package ru.svoyakmartin.featureCharacter.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.svoyakmartin.featureCharacter.domain.model.Character


@Dao
interface CharacterRoomDAO {
    @Query("SELECT * FROM characters")
    fun getAllCharacters(): Flow<List<Character>?>

    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacterById(id: Int): Flow<Character?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: Character)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)
}