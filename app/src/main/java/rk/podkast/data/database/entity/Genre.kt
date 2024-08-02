package rk.podkast.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import rk.podkast.data.GenreType
import rk.podkast.data.database.Converter

@Entity(tableName = "interest_genre")
data class Genre(
    @PrimaryKey
    @TypeConverters(Converter::class) val type: GenreType
)
