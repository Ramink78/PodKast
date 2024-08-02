package rk.podkast.data

import kotlinx.coroutines.flow.Flow
import rk.podkast.data.database.entity.Genre

interface GenreRepository {
    fun getAllGenres(): List<Genre>
    suspend fun toggleGenre(genre: Genre)
    fun interestedGenresFlow(): Flow<List<Genre>>
}