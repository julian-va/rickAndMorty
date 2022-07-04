package com.example.rickandmorty.domain.useCase

import com.example.rickandmorty.data.Result
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): Result<Character> {
        return characterRepository.getCharacter(id = id)
    }
}