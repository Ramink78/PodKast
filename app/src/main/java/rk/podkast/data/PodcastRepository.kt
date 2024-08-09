package rk.podkast.data

import rk.podkast.data.model.EpisodeDto
import rk.podkast.data.model.PopularPodcastsDto
import rk.podkast.data.remote.util.DataError
import rk.podkast.data.remote.util.Response

interface PodcastRepository {
    suspend fun getPopularPodcasts(): Response<PopularPodcastsDto, DataError>
    suspend fun getRandomEpisode(): Response<EpisodeDto, DataError>
}