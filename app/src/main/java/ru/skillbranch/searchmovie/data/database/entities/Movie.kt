package ru.skillbranch.searchmovie.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movies")
data class Movie(
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
    @ColumnInfo(name = "background_image")
    var backgroundImage: String = "",
    @ColumnInfo(name = "genres_ids")
    var genresIds: List<Int> = listOf(),
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (movieId != other.movieId) return false
        if (releaseDate != other.releaseDate) return false
        if (title != other.title) return false
        if (imageUrl != other.imageUrl) return false
        if (description != other.description) return false
        if (genresIds != other.genresIds) return false
        if (rateScore != other.rateScore) return false
        if (ageLimit != other.ageLimit) return false

        return true
    }

    override fun hashCode(): Int {
        var result = movieId
        result = 31 * result + releaseDate.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + genresIds.hashCode()
        result = 31 * result + rateScore.hashCode()
        result = 31 * result + ageLimit
        return result
    }
}