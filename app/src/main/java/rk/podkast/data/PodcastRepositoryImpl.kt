package rk.podkast.data

import rk.podkast.data.model.EpisodeDto
import rk.podkast.data.model.PopularPodcastsDto
import rk.podkast.data.remote.ServiceApi
import rk.podkast.data.remote.util.DataError
import rk.podkast.data.remote.util.Response

class PodcastRepositoryImpl(
    private val serviceApi: ServiceApi
) : PodcastRepository {
    override suspend fun getPopularPodcasts(): Response<PopularPodcastsDto, DataError> {
        return serviceApi.getPopularPodcasts()
    }

    override suspend fun getRandomEpisode(): Response<EpisodeDto, DataError> {
        return serviceApi.getRandomEpisode()
    }
}