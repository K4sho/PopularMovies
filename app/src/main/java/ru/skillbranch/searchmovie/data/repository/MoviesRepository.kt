package ru.skillbranch.searchmovie.data.repository

import ru.skillbranch.searchmovie.data.sources.movies.IMoviesDataSource

class MoviesRepository(
    private val moviesDataSource: IMoviesDataSource
) {
    fun getMovies() = moviesDataSource.getMovies()
}