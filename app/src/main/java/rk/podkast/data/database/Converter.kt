package rk.podkast.data.database

import androidx.room.TypeConverter
import rk.podkast.data.GenreType

class Converter {

    @TypeConverter
    fun toGenreType(name: String) = enumValueOf<GenreType>(name)

    @TypeConverter
    fun fromGenreType(type: GenreType) = type.name
}