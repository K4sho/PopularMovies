package ru.skillbranch.searchmovie.data.database.typeconvertes

import androidx.room.TypeConverter

class ListIntConverter {

    @TypeConverter
    fun fromListIntToString(data: List<Int>): String {
        return data.joinToString()
    }

    @TypeConverter
    fun toListIntFromString(data: String): List<Int> {
        val listInt = mutableListOf<Int>()
        return if (data.isNotEmpty()) {
            data.splitToSequence(", ").toList().forEach {
                listInt.add(it.toInt())
            }
            listInt
        } else {
            listOf<Int>()
        }

    }
}