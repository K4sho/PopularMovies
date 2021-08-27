package ru.skillbranch.searchmovie.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(primaryKeys = ["movie_id", "actor_id"])
data class MovieActorCrossRef(
    @ColumnInfo(name = "movie_id")
    var movieId: Int,
    @ColumnInfo(name = "actor_id")
    var actorId: String
)