package rk.podkast.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import rk.podkast.data.GenreType
import rk.podkast.data.database.entity.Genre

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun interest(genre: Genre)

    @Delete
    suspend fun notInterest(genre: Genre)

    @Query("select * from interest_genre")
    fun interestedGenresFlow(): Flow<List<Genre>>

    @Query("select * from interest_genre where type = :type")
    suspend fun isExist(type: GenreType): List<Genre>
}