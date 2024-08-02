package rk.podkast.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import rk.podkast.data.database.GenreDao
import rk.podkast.data.database.entity.Genre

class GenreRepositoryImpl(
    private val genreDao: GenreDao,
    private val appContext: Context
) : GenreRepository {
    override fun getAllGenres(): List<Genre> {
        return GenreType.entries.sortedBy {
            appContext.getString(it.nameRes)
        }
            .map { Genre(it) }
    }

    override suspend fun toggleGenre(genre: Genre) {
        if (isInterested(genre))
            genreDao.notInterest(genre)
        else
            genreDao.interest(genre)

    }

    override fun interestedGenresFlow(): Flow<List<Genre>> {
        return genreDao.interestedGenresFlow()
    }

    private suspend fun isInterested(genre: Genre) =
        genreDao.isExist(genre.type).isNotEmpty()

}