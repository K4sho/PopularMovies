package ru.skillbranch.searchmovie.data.repository

import kotlinx.coroutines.*
import ru.skillbranch.searchmovie.App
import ru.skillbranch.searchmovie.AppConfig.API_KEY
import ru.skillbranch.searchmovie.AppConfig.API_PAGE_SIZE
import ru.skillbranch.searchmovie.AppConfig.LANGUAGE_API
import ru.skillbranch.searchmovie.data.database.entities.Actor
import ru.skillbranch.searchmovie.data.database.entities.Movie
import ru.skillbranch.searchmovie.data.database.entities.MovieActorCrossRef
import ru.skillbranch.searchmovie.data.utils.toActorDb
import ru.skillbranch.searchmovie.data.utils.toMovieDb

class MoviesRepository {

    private val movieDao = App.database.movieAppDao()
    private val movieApi = App.apiHelper

    private var pageCounter = 1

    suspend fun getMovies() = movieDao.getAllMovies()

    /// Получим обновленные данные из сети
    suspend fun getMoviesFromApi(): List<Movie> {
        val movies = mutableListOf<Movie>()
        withContext(Dispatchers.IO) {
            if (pageCounter + 1 > API_PAGE_SIZE) {
                pageCounter = 1
            }
            val moviesResponse = movieApi.getMoviesPopular(
                apiKey = API_KEY,
                language = LANGUAGE_API,
                page = pageCounter++
            )
            moviesResponse.results.forEach {
                movies.add(it.toMovieDb(context = App.applicationContext()))
            }
        }
        movieDao.insertMovies(movies)
        return movies
    }

    suspend fun getMovieWithActorsFromDB(movieId: Int) = movieDao.getMovieWithActors(movieId)

    suspend fun getMovieActorsFromAPI(movieId: Int): List<Actor> {
        val actors: ArrayList<Actor> = arrayListOf<Actor>()
        val cast =
            movieApi.getActorsList(
                movieId = movieId,
                apiKey = API_KEY,
                language = LANGUAGE_API
            ).castPerson
        cast.forEach {
            val actor = it.toActorDb(App.applicationContext())
            actors.add(actor)
            movieDao.insertMovieActorCrossRef(MovieActorCrossRef(movieId, actor.actorId))
        }
        movieDao.insertActors(actors)
        return actors
    }
}