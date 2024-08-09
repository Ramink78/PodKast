package rk.podkast.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rk.podkast.data.PodcastRepositoryImpl
import rk.podkast.data.model.EpisodeDto
import rk.podkast.data.model.PodcastDto
import rk.podkast.data.remote.ServiceApiImpl
import rk.podkast.data.remote.util.Response

class HomeViewModel : ViewModel() {
    private val repo = PodcastRepositoryImpl(ServiceApiImpl())
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadHomeContent()
        }
    }

    private suspend fun loadHomeContent() {
        _uiState.update { it.copy(isLoading = true) }
        loadBannerEpisode()
        loadPopularPodcasts()
    }

    private suspend fun loadBannerEpisode() {
        when (val randomEpisodeResult = repo.getRandomEpisode()) {
            is Response.Failure -> {
                // TODO
            }

            is Response.Success -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        banner = listOf(randomEpisodeResult.data)
                    )
                }
            }
        }
    }

    private suspend fun loadPopularPodcasts() {
        when (val popularPodcastsResult = repo.getPopularPodcasts()) {
            is Response.Failure -> {
                // TODO
            }

            is Response.Success -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        popularPodcasts = popularPodcastsResult.data.podcasts
                    )
                }
            }
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val popularPodcasts: List<PodcastDto> = emptyList(),
    val banner: List<EpisodeDto> = emptyList()
)