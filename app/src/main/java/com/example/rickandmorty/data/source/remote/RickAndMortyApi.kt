package com.example.rickandmorty.data.source.remote

import com.example.rickandmorty.data.source.remote.dto.CharacterDTO
import com.example.rickandmorty.data.source.remote.dto.CharactersDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET(value = "character/")
    suspend fun getCharacters(
        @Query("page") page: Int
    ):Response<CharactersDto>

    @GET(value = "character/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int
    ):Response<CharacterDTO>
}