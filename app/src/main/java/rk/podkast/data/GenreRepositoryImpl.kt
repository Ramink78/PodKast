package rk.podkast.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import rk.podkast.data.database.GenreDao
import rk.podkast.data.database.entity.GenreEntity

class GenreRepositoryImpl(
    private val genreDao: GenreDao,
) : GenreRepository {
    override fun getAllGenres(): List<GenreEntity> {
        return GenreType.entries.map {
            GenreEntity(type = it)
        }
    }

    override suspend fun interest(genreEntity: GenreEntity) {
        genreDao.interest(genreEntity)
    }

    override suspend fun notInterest(genreEntity: GenreEntity) {
        genreDao.notInterest(genreEntity)
    }


    override fun interestedGenresFlow(): Flow<List<GenreEntity>> {
        return genreDao.interestedGenresFlow()
    }

    override fun notInterestGenresFlow(): Flow<List<GenreEntity>> {
        return genreDao.interestedGenresFlow().transform {
            emit(getAllGenres().subtract(it.toSet()).toList())
        }
    }

}