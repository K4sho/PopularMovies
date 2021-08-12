package ru.skillbranch.searchmovie.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Actors")
data class Actor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "actor_id")
    var id: Int,
    @ColumnInfo(name = "name")
    var actorName: String,
    @ColumnInfo(name = "url_photo")
    var photo: String
)