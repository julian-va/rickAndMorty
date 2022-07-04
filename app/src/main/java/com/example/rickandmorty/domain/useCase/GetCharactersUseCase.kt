package com.example.rickandmorty.domain.useCase

import com.example.rickandmorty.data.Result
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.model.Characters
import com.example.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
){
    suspend operator fun invoke(page: Int): Flow<Result<List<Characters>>>{
        return characterRepository.getCharacters(page = page)
    }
}