package ru.skillbranch.searchmovie.data.network

import ru.skillbranch.searchmovie.data.remote_res.ActorRes
import ru.skillbranch.searchmovie.data.remote_res.GenreRes
import ru.skillbranch.searchmovie.data.remote_res.MovieRes
import ru.skillbranch.searchmovie.data.remote_res.MoviesListRes
import java.io.IOException

// Вспомогательная функция. Позволяет вызвать метод запроса в корутине и передать сообщение, которое будет выброшено при ошибке
suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> =
    try {
        call.invoke()
    } catch (e: Exception) {
        Result.Error(IOException(errorMessage, e))
    }

class ApiHelper(private val apiService: ApiService) {
    suspend fun getMovies() = safeApiCall(
        call = { fetchMovies() },
        errorMessage = "Get Movies Error"
    )

    suspend fun getActorsByMovieId(movieId: Int) = safeApiCall(
        call = { fetchActors(movieId) },
        errorMessage = "Get Actors Error"
    )

    suspend fun getGenres() = safeApiCall(
        call = { fetchGenres() },
        errorMessage = "Get Genres Error"
    )

    private suspend fun fetchMovies(): Result<MoviesListRes> {
        val response = apiService.getMoviesPopular()
        if (response.isSuccessful) {
            return response.body()?.let { Result.Success(it) }!!
        }
        return Result.Error(IOException("Ошибка при запросе фильмов"))
    }

    private suspend fun fetchActors(movieId: Int): Result<ActorRes> {
        val response = apiService.getActorsList(movieId)
        if (response.isSuccessful) {
            return response.body()?.let { Result.Success(it) }!!
        }
        return Result.Error(IOException("Ошибка при запросе актеров"))
    }

    private suspend fun fetchGenres(): Result<GenreRes> {
        val response = apiService.getGenresList()
        if (response.isSuccessful) {
            return response.body()?.let { Result.Success(it) }!!
        }
        return Result.Error(IOException("Ошибка при запросе жанров"))
    }
}