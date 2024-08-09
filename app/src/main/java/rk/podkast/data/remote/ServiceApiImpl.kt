package rk.podkast.data.remote

import kotlinx.serialization.json.Json
import rk.podkast.data.model.EpisodeDto
import rk.podkast.data.model.PopularPodcastsDto
import rk.podkast.data.remote.util.DataError
import rk.podkast.data.remote.util.Response

class ServiceApiImpl : ServiceApi {
    private val json = Json(builderAction = {
        isLenient = true
        allowTrailingComma=true
        ignoreUnknownKeys = true
    })

    override suspend fun getPopularPodcasts(): Response<PopularPodcastsDto, DataError> {
        return Response.Success(
            data = json.decodeFromString<PopularPodcastsDto>(
                sampleResponseBestPodcasts
            )
        )
    }

    override suspend fun getRandomEpisode(): Response<EpisodeDto, DataError> {
        return Response.Success(data = json.decodeFromString(sampleResponseForRandomPodcast))
    }
}