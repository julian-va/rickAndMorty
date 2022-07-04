package com.example.rickandmorty.ui.component.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.Result
import com.example.rickandmorty.domain.useCase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {
    var state by mutableStateOf(HomeState(isLoading = true))
        private set

    private val _evenFlow = MutableSharedFlow<UIEvent>()
    val evenFlow = _evenFlow.asSharedFlow()

    private var currentPage = 1

    init {
        getCharacter(increase = false)
    }

    fun getCharacter(increase: Boolean) {
        viewModelScope.launch() {
            if (increase) {
                currentPage++
            } else if (currentPage > 1) {
                currentPage--
            }
            val showPrevious = currentPage > 1
            val showNext = currentPage < 42
            getCharactersUseCase(currentPage).onEach { result ->
                when (result) {
                    is Result.Success -> {
                        state = state.copy(
                            character = result.data ?: emptyList(),
                            showPrevious = showPrevious,
                            isLoading = false,
                            showNext = showNext
                        )
                    }
                    is Result.Error -> {
                        state = state.copy(
                            isLoading = false
                        )
                        _evenFlow.emit(
                            UIEvent.ShowSnackBar(
                                result.message ?: "Sucedio un error!!"
                            )
                        )
                    }
                    is Result.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String) : UIEvent()
    }
}