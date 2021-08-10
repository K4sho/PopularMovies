package ru.skillbranch.searchmovie.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Actors")
data class Actor (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "name")
    var actorName: String,
    @ColumnInfo(name = "url_photo")
    var photo: String,
)