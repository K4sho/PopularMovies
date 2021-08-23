package ru.skillbranch.searchmovie.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Profile")
data class Profile(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "password")
    var password: String?,
    @ColumnInfo(name = "email")
    var email: String?,
    @ColumnInfo(name = "phone")
    var phone: String?,
    @ColumnInfo(name = "interests")
    var interests: List<String> = listOf()
)