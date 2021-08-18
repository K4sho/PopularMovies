package ru.skillbranch.searchmovie.data.remote_res

data class ResultMoviesList(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

data class MovieRes(
    val page: Int,
    val results: List<ResultMoviesList>,
    val total_pages: Int,
    val total_results: Int
)