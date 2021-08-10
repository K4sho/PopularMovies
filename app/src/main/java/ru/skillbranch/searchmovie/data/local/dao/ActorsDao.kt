package ru.skillbranch.searchmovie.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.skillbranch.searchmovie.data.local.entities.Actor

@Dao
interface ActorsDao {
    @Query("SELECT * FROM Actors")
    fun getActors(): LiveData<List<Actor>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(actors: Actor)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDefault(actors: Actor)

    @Query("DELETE FROM Actors")
    suspend fun deleteAllActors()
}