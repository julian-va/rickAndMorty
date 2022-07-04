package com.example.rickandmorty.ui.component.home

import com.example.rickandmorty.domain.model.Characters

data class HomeState(
    val character: List<Characters> = emptyList(),
    val showPrevious:Boolean = false,
    val showNext:Boolean = false,
    val isLoading:Boolean = false,
)
