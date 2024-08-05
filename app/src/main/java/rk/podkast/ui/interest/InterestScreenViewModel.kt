package rk.podkast.ui.interest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rk.podkast.data.GenreRepository
import rk.podkast.data.database.entity.GenreEntity

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
        MutableStateFlow(InterestScreenUiState())
    val uiState = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            genreRepository.interestedGenresFlow().collect { newList ->
                _uiState.update { it.copy(interestGenreEntities = newList) }
            }
        }
        viewModelScope.launch {
            genreRepository.notInterestGenresFlow().collect { newList ->
                _uiState.update { it.copy(notInterestedGenreEntities = newList) }
            }
        }
    }

    fun interest(genre: GenreEntity) {
        viewModelScope.launch {
            genreRepository.interest(genre)
        }
    }

    fun notInterest(genre: GenreEntity) {
        viewModelScope.launch {
            genreRepository.notInterest(genre)
        }
    }

}

data class InterestScreenUiState(
    val interestGenreEntities: List<GenreEntity> = emptyList(),
    val notInterestedGenreEntities: List<GenreEntity> = emptyList(),
)
