package ru.skillbranch.searchmovie.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.skillbranch.searchmovie.data.remote_res.ActorRes
import ru.skillbranch.searchmovie.data.remote_res.GenreRes
import ru.skillbranch.searchmovie.data.remote_res.MoviesListRes

interface ApiService {
    @GET("movie/popular")
    suspend fun getMoviesPopular(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): MoviesListRes

    @GET("genre/movie/list")
    suspend fun getGenresList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): GenreRes

    @GET("movie/{movieId}/credits")
    suspend fun getActorsList(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): ActorRes
}