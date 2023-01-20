package ru.svoyakmartin.rickandmortyapi.data.repository

import ru.svoyakmartin.rickandmortyapi.data.db.RoomDAO
import ru.svoyakmartin.rickandmortyapi.domain.models.Character

class Repository(private  val roomDAO: RoomDAO){
    val allCharacters = roomDAO.getAllCharacters()

    suspend fun insertCharacter(character: Character){
        roomDAO.insertCharacter(character)
    }

    suspend fun deleteCharacter(character: Character){
        roomDAO.deleteCharacter(character)
    }

    suspend fun deleteAllCharacters(){
        roomDAO.deleteAllCharacters()
    }
}