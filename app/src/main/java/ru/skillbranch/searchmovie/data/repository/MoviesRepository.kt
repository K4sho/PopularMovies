package ru.skillbranch.searchmovie.data.repository

import kotlinx.coroutines.*
import ru.skillbranch.searchmovie.App
import ru.skillbranch.searchmovie.data.database.entities.Movie
import ru.skillbranch.searchmovie.data.database.entities.MoviesWithActors

class MoviesRepository {
    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }
    // SupervisorJob() нужен для того, что бы при краше дочерней корутины не закрывались все остальные
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO + handler)
    private val movieDao = App.database.movieAppDao

    suspend fun getMovies() = movieDao.getAllMovies()

    suspend fun getRefreshMovies(): List<Movie> {
        delay(2000)
        return movieDao.getAllMovies().shuffled()
    }

    suspend fun getMovieWitchActors(movieId: Int): MoviesWithActors {
        return movieDao.getMoviesWithActors().filter { it.movie.movieId == movieId }.first()
    }

    suspend fun getMovieById(movieId: Int) = movieDao.getAllMovies().find { it.movieId == movieId }
}