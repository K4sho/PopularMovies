package ru.skillbranch.searchmovie.data.repository

import kotlinx.coroutines.delay
import ru.skillbranch.searchmovie.App
import ru.skillbranch.searchmovie.data.database.MovieDatabase
import ru.skillbranch.searchmovie.data.dto.MovieDto
import ru.skillbranch.searchmovie.data.sources.movies.IMoviesDataSource

class MoviesRepository(
    private val moviesDataSource: IMoviesDataSource
) {
    private val movieDao = MovieDatabase.getInstance(App.applicationContext()).movieAppDao
    private var movies = moviesDataSource.getMovies()

    suspend fun getMovies() = movieDao.getMoviesWithActors()

    suspend fun getRefreshMovies(): List<MovieDto> {
        delay(2000)
        movies = movies.shuffled()
        return movies
    }

    fun getMovieById(movieId: Int): MovieDto? {
        return movies.find { it.id == movieId }
    }
}