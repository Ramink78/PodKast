package rk.podkast.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import rk.podkast.data.GenreRepository
import rk.podkast.data.GenreType
import rk.podkast.data.database.entity.Genre

class FakeGenreRepository : GenreRepository {
    private val interestGenres = mutableListOf<Genre>()
    private val observableGenres = MutableStateFlow<List<Genre>>(listOf())
    fun addGenresToInterestForTest(genres: List<Genre>) {
        interestGenres.addAll(genres)
        forceRefresh()
    }

    override fun getAllGenres(): List<Genre> {
        return GenreType.entries.map { Genre(it) }
    }

    override suspend fun toggleGenre(genre: Genre) {
        if (interestGenres.contains(genre)) interestGenres.remove(genre)
        else interestGenres.add(genre)
        forceRefresh()
    }

    override fun interestedGenresFlow(): Flow<List<Genre>> {
        return observableGenres
    }

    private fun forceRefresh() = runBlocking {
        observableGenres.emit(interestGenres)
    }
}

