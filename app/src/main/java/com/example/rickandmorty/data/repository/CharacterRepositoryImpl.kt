package com.example.rickandmorty.data.repository

import com.example.rickandmorty.data.Result
import com.example.rickandmorty.data.source.remote.RickAndMortyApi
import com.example.rickandmorty.data.source.remote.dto.ToCharacter
import com.example.rickandmorty.data.source.remote.dto.toListCharacters
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.model.Characters
import com.example.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api:RickAndMortyApi
) : CharacterRepository {
    override fun getCharacters(page: Int): Flow<Result<List<Characters>>> = flow {
        emit(Result.Loading())
        try {
            val response = api.getCharacters(page = page)
            if (response.isSuccessful){
                response.body()?.let { emit(Result.Success(it.toListCharacters())) }
            }
        }catch (e:IOException){
            emit(Result.Error(
                message = "Algo salio mal en el llamado",
                data = null
            ))
        }
    }

    override suspend fun getCharacter(id: Int): Result<Character> {
        try {
            val response = api.getCharacter(id = id)
            if (response.isSuccessful){
                response.body()?.let { return Result.Success(it.ToCharacter()) }
            }
        }catch (e:IOException){
        }
        return Result.Success(null)
    }
}