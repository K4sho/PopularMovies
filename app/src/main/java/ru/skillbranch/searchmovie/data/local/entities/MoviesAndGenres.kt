package ru.skillbranch.searchmovie.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Relation

@Entity(tableName = "MoviesAndGenres")
data class MoviesAndGenres(
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "rate_score")
    var rateScore: Int?,
    @ColumnInfo(name = "age_restriction")
    var ageLimit: String?,
    @ColumnInfo(name = "image_url")
    var imageUrl: String?,
    @ColumnInfo(name = "release_date")
    var releaseDate: String?,
    @ColumnInfo(name = "genre")
    var genre: String,
    @ColumnInfo(name = "actor_id")
    var actorId: Int,
    @Relation(parentColumn = "actor_id", entityColumn = "actor_id")
    var actorsList: List<Actor>
)