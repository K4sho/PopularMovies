package ru.skillbranch.searchmovie.presentation.view_models

import ru.skillbranch.searchmovie.data.sources.movies.IMoviesDataSource

class MoviesViewModel(
    private val moviesDataSource: IMoviesDataSource
) {
    fun getMovies() = moviesDataSource.getMovies()
}