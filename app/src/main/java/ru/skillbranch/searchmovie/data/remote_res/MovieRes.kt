package ru.skillbranch.searchmovie.data.remote_res

import com.google.gson.annotations.SerializedName

data class MovieRes(
    @SerializedName("adult") var adult: Boolean?,
    @SerializedName("backdrop_path")  var backgroundImagePath: String?,
    @SerializedName("genre_ids")  var genreIds: List<Int>?,
    @SerializedName("id")  var idMovie: Int,
    @SerializedName("original_language")  var originalLanguage: String,
    @SerializedName("original_title")  var originalTitle: String,
    @SerializedName("overview")  var overview: String?,
    @SerializedName("poster_path")  var posterImagePath: String?,
    @SerializedName("release_date")  var releaseDate: String?,
    @SerializedName("title")  var title: String,
    @SerializedName("vote_average")  var voteAverage: Double
)

data class MoviesListRes(
    @SerializedName("page") var page: Int,
    @SerializedName("results") var results: List<MovieRes>,
)