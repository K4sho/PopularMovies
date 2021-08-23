package ru.skillbranch.searchmovie.data.repository

import kotlinx.coroutines.*
import ru.skillbranch.searchmovie.App
import ru.skillbranch.searchmovie.data.dto.MovieDto
import ru.skillbranch.searchmovie.data.utils.toMovie

class MoviesRepository {
    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }

    // SupervisorJob() нужен для того, что бы при краше дочерней корутины не закрывались все остальные
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO + handler)
    private val movieDao = App.database.movieAppDao

    suspend fun getMovies() = movieDao.getAllMovies().map { it.toMovie() }

    suspend fun getRefreshMovies(): List<MovieDto> {
        delay(2000)
        return movieDao.getAllMovies().map { it.toMovie() }.shuffled()
    }

    suspend fun getMovieWitchActors(movieId: Int): MovieDto {
        return movieDao.getMoviesWithActors().first { it.movie.movieId == movieId }.toMovie()
    }
}