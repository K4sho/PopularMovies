package ru.skillbranch.searchmovie.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Actors")
data class Actor(
    @PrimaryKey
    @ColumnInfo(name = "actor_id")
    var actorId: Int,
    @ColumnInfo(name = "name")
    var actorName: String,
    @ColumnInfo(name = "url_photo")
    var photo: String
)