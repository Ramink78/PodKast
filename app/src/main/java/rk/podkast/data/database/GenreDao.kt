package rk.podkast.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import rk.podkast.data.database.entity.GenreEntity

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun interest(genreEntity: GenreEntity)

    @Delete
    suspend fun notInterest(genreEntity: GenreEntity)

    @Query("select * from interest_genre")
    fun interestedGenresFlow(): Flow<List<GenreEntity>>

}