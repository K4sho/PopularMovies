package ru.skillbranch.searchmovie.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.skillbranch.searchmovie.data.remote_res.ActorRes
import ru.skillbranch.searchmovie.data.remote_res.GenreRes
import ru.skillbranch.searchmovie.data.remote_res.MoviesListRes

interface ApiService {
    @GET("movie/popular?api_key=296e24d37c4504d69452c574ad7ec88d&language=ru-Ru")
    suspend fun getMoviesPopular(@Query("page") page: Int): MoviesListRes

    @GET("genre/movie/list?api_key=296e24d37c4504d69452c574ad7ec88d&language=ru-Ru")
    suspend fun getGenresList(): GenreRes

    @GET("movie/{moviesId}/credits?api_key=296e24d37c4504d69452c574ad7ec88d&language=ru-Ru")
    suspend fun getActorsList(
        @Path("moviesId")
        moviesId: Int): ActorRes
}