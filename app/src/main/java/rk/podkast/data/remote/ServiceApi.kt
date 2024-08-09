package rk.podkast.data.remote

import rk.podkast.data.model.EpisodeDto
import rk.podkast.data.model.PopularPodcastsDto
import rk.podkast.data.remote.util.DataError
import rk.podkast.data.remote.util.Response

interface ServiceApi {
    suspend fun getPopularPodcasts(): Response<PopularPodcastsDto, DataError>
    suspend fun getRandomEpisode(): Response<EpisodeDto, DataError>
}