package rk.podkast.ui.interest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rk.podkast.data.GenreRepository
import rk.podkast.data.GenreType
import rk.podkast.data.database.entity.Genre

class InterestScreenViewModel(private val genreRepository: GenreRepository) : ViewModel() {

    companion object {
        @Suppress("UNCHECKED_CAST")
        class Factory(private val genreRepository: GenreRepository) :
            ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return InterestScreenViewModel(genreRepository) as T
            }
        }
    }

    private val _uiState =
        MutableStateFlow(InterestScreenUiState(allGenres = genreRepository.getAllGenres()))
    val uiState = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            genreRepository.interestedGenresFlow().collect { newList ->
                _uiState.update { it.copy(interestGenres = newList) }
            }
        }
    }


    fun toggleGenre(genreKey: GenreType) {
        viewModelScope.launch {
            genreRepository.toggleGenre(Genre(genreKey))
        }
    }

}

data class InterestScreenUiState(
    val allGenres: List<Genre> = emptyList(),
    val interestGenres: List<Genre> = emptyList(),
)
