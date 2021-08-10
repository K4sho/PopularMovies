package ru.skillbranch.searchmovie.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.skillbranch.searchmovie.data.local.entities.Profile

@Dao
interface ProfileDao {
    @Query("SELECT * from Profile")
    fun getProfilesList(): LiveData<List<Profile>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(profile: Profile)

    @Update
    suspend fun updateProfile(profile: Profile)

    @Query("DELETE FROM Profile")
    suspend fun deleteAllProfiles()
}