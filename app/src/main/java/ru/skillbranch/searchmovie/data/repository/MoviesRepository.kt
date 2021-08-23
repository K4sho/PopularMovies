package ru.skillbranch.searchmovie.data.repository

import kotlinx.coroutines.*
import ru.skillbranch.searchmovie.App
import ru.skillbranch.searchmovie.data.database.entities.MovieActorCrossRef
import ru.skillbranch.searchmovie.data.dto.MovieDto
import ru.skillbranch.searchmovie.data.remote_res.ActorRes
import ru.skillbranch.searchmovie.data.remote_res.MovieRes
import ru.skillbranch.searchmovie.data.remote_res.MoviesListRes
import ru.skillbranch.searchmovie.data.utils.toMovie
import ru.skillbranch.searchmovie.data.network.Result
import ru.skillbranch.searchmovie.data.utils.toActorDb
import ru.skillbranch.searchmovie.data.utils.toMovieDb

class MoviesRepository {
    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }

    // SupervisorJob() нужен для того, что бы при краше дочерней корутины не закрывались все остальные
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO + handler)
    private val movieDao = App.database.movieAppDao()
    private val movieApi = App.apiHelper

    suspend fun getMovies() = movieDao.getAllMovies().map { it.toMovie() }

    /// Метод для проверки нужно дли обновить БД. Просто проверим есть ли там данные
    suspend fun needUpdate(): Boolean = App.database.isEmpty()

    /// Метод для заполнения таблиц данными из сети
    suspend fun fillDataFromNetwork() {
        /// Получаем пары - (фильм, список актеров)
        val moviesFromNetwork = getMoviesWithActors()
        coroutineScope.launch {
            // Заполняем актеров
            moviesFromNetwork.forEach { moviesNet ->
                launch {
                    /// Здесь нужно замапить в формат для БД и вставить фильмы, актеров и связь между ними
                    val movie = moviesNet.first.toMovieDb()
                    movieDao.insert(movie)
                    val actors = moviesNet.second.castPerson.map { it.toActorDb() }
                    movieDao.insertActors(actors)
                    actors.forEach {
                        actor ->
                        val movieAndActors = MovieActorCrossRef(movie.movieId, actor.actorId)
                        movieDao.insertMovieActorCrossRef(movieAndActors)
                    }
                }
            }
        }.join()
    }

    suspend fun getRefreshMovies(): List<MovieDto> {
        delay(2000)
        return movieDao.getAllMovies().map { it.toMovie() }.shuffled()
    }

    suspend fun getMovieWitchActors(movieId: Int): MovieDto {
        return movieDao.getMoviesWithActors().first { it.movie.movieId == movieId }.toMovie()
    }

    /// Метод возвращает фильм и список актеров к нему
    private suspend fun getMoviesWithActors(): List<Pair<MovieRes, ActorRes>> {
        val resultList = mutableListOf<Pair<MovieRes, ActorRes>>()
        // Получим список фильмов
        val movies = getNeedMoviesInternal()
        coroutineScope.launch {
            movies.forEach { movie ->
                // Теперь для каждого дома получаем список актеров и добавляем в результат
                val response = movieApi.getActorsByMovieId(movie.idMovie)
                if (response is Result.Success) {
                    val netResult = response.data
                    netResult.let {
                        resultList.add(movie to it)
                    }
                }
            }
        }.join()
        return resultList
    }

    /// Метод запрашивает список домов через АПИ по именам
    private suspend fun getNeedMoviesInternal(): List<MovieRes> {
        val resultList = mutableListOf<MovieRes>()
        coroutineScope.launch {
            val response = movieApi.getMovies()
            if (response is Result.Success) {
                val netResult = response.data.results
                netResult.let {
                    resultList.addAll(it)
                }
            }
        }.join()
        return resultList
    }
}