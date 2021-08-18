package ru.skillbranch.searchmovie.data.remote_res

data class GenreApiResult(
    val id: Int,
    val name: String
)

data class GenreRes(
    val genres: List<GenreApiResult>
)