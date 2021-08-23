package ru.skillbranch.searchmovie.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.skillbranch.searchmovie.data.dto.ActorDto
import ru.skillbranch.searchmovie.data.dto.CategoryDto

@Entity(tableName = "Movies")
data class Movie (
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
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
    @Embedded
    val genre: CategoryDto,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
)