package ru.skillbranch.searchmovie.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.skillbranch.searchmovie.data.local.entities.Genre

@Dao
interface GenresDao {
    @Query("SELECT * from Genres")
    fun getGenres(): LiveData<List<Genre>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(genres: Genre)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDefault(genres: Genre)

    @Query("DELETE FROM Genres")
    suspend fun deleteAllGenres()
}