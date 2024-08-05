package rk.podkast.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking
import rk.podkast.data.GenreRepository
import rk.podkast.data.GenreType
import rk.podkast.data.database.entity.GenreEntity

class FakeGenreRepository : GenreRepository {
    private val interestGenreEntities = mutableListOf<GenreEntity>()
    private val observableGenres = MutableStateFlow<List<GenreEntity>>(listOf())
    fun addGenresToInterestForTest(genreEntities: List<GenreEntity>) {
        interestGenreEntities.addAll(genreEntities)
        forceRefresh()
    }

    override fun getAllGenres(): List<GenreEntity> {
        return GenreType.entries.map { GenreEntity(it) }
    }

    override suspend fun interest(genreEntity: GenreEntity) {
        interestGenreEntities.add(genreEntity)
        forceRefresh()
    }

    override suspend fun notInterest(genreEntity: GenreEntity) {
        interestGenreEntities.remove(genreEntity)
        forceRefresh()
    }

    override fun interestedGenresFlow(): Flow<List<GenreEntity>> {
        return observableGenres
    }

    override fun notInterestGenresFlow(): Flow<List<GenreEntity>> {
        return observableGenres.transform {
            emit(getAllGenres().subtract(it.toSet()).toList())
        }
    }

    private fun forceRefresh() = runBlocking {
        observableGenres.emit(interestGenreEntities)
    }
}

