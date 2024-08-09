package rk.podkast.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeDto(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("image") val image: String
)
