package rk.podkast.data

import kotlinx.coroutines.flow.Flow
import rk.podkast.data.database.entity.GenreEntity

interface GenreRepository {
    fun getAllGenres(): List<GenreEntity>
    suspend fun interest(genreEntity: GenreEntity)
    suspend fun notInterest(genreEntity: GenreEntity)
    fun interestedGenresFlow(): Flow<List<GenreEntity>>
    fun notInterestGenresFlow(): Flow<List<GenreEntity>>
}