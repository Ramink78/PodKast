package rk.podkast.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import rk.podkast.data.database.entity.Genre

@Database(entities = [Genre::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao

    companion object {
        private val lock = Any()

        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(appContext: Context): AppDatabase {
            synchronized(lock) {
                return INSTANCE ?: Room.databaseBuilder(
                    appContext, AppDatabase::class.java, "PodKastDB"
                ).build().also { INSTANCE = it }
            }

        }
    }
}