package ru.skillbranch.searchmovie.data.sources.movies

import ru.skillbranch.searchmovie.data.dto.MovieDto

interface IMoviesDataSource {
    fun getMovies(): List<MovieDto>
}