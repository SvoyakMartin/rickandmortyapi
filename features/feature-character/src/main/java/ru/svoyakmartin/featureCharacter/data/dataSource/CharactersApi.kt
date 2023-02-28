package ru.svoyakmartin.featureCharacter.data.dataSource

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.svoyakmartin.coreNetwork.provider.response.ApiResponse
import ru.svoyakmartin.featureCharacter.data.model.CharacterDTO
import ru.svoyakmartin.featureCharacter.data.model.CharactersDTO

interface CharactersApi {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): ApiResponse<CharactersDTO>

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): ApiResponse<CharacterDTO>

    @GET("character/[{id}]")
    suspend fun getCharactersByIds(@Path("id") ids: String): ApiResponse<List<CharacterDTO>>
}