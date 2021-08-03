package ru.skillbranch.searchmovie.data.repository

import kotlinx.coroutines.delay
import ru.skillbranch.searchmovie.data.dto.MovieDto
import ru.skillbranch.searchmovie.data.sources.movies.IMoviesDataSource

class MoviesRepository(
    private val moviesDataSource: IMoviesDataSource
) {
    private var movies = moviesDataSource.getMovies()

    fun getMovies() = movies

    suspend fun getRefreshMovies(): List<MovieDto> {
        delay(2000)
        movies = movies.shuffled()
        return movies
    }
}