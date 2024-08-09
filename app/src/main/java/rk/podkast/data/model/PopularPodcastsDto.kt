package rk.podkast.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularPodcastsDto(
    @SerialName("podcasts")
    val podcasts: List<PodcastDto>
)
