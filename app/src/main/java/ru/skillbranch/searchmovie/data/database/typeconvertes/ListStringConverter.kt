package ru.skillbranch.searchmovie.data.database.typeconvertes

import androidx.room.TypeConverter

class ListStringConverter {
    @TypeConverter
    fun fromListString(data: List<String>): String {
        return data.joinToString()
    }

    @TypeConverter
    fun toListString(data: String): List<String> {
        return data.splitToSequence(", ").toList()
    }
}