package ru.skillbranch.searchmovie.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movies")
data class Movie (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val movieId: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "rate_score")
    val rateScore: Int,
    @ColumnInfo(name = "age_restriction")
    val ageLimit: Int,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "genre_id")
    val genre: Int,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    @ColumnInfo(name = "actor_id")
    val actor: Int
)