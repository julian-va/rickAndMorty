package com.example.rickandmorty.data.source.remote.dto

import com.example.rickandmorty.domain.model.Character

data class CharacterDTO(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)
fun CharacterDTO.ToCharacter():Character{
    return Character(
        id = id,
        name = name,
        gender = gender,
        image = image,
        location = location,
        origin = origin,
        species = species,
        status = status
    )
}