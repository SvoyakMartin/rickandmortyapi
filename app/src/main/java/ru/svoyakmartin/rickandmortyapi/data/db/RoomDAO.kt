package ru.svoyakmartin.rickandmortyapi.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.svoyakmartin.rickandmortyapi.domain.models.Character

@Dao
interface RoomDAO {
    @Query("SELECT * FROM characters")
    fun getAllCharacters(): Flow<List<Character>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: Character)

    @Delete
    suspend fun deleteCharacter(character: Character)

    @Query("DELETE FROM characters")
    suspend fun deleteAllCharacters()
}