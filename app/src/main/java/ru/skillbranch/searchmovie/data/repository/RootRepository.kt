package ru.skillbranch.searchmovie.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.skillbranch.searchmovie.data.database.MovieDatabase
import ru.skillbranch.searchmovie.data.sources.movies.ActorsDataSourceDefault
import ru.skillbranch.searchmovie.data.sources.movies.MoviesDataSourceDefault

class RootRepository(private val db: MovieDatabase) {

    /// Заполнить базу данных статическими данными
    fun fillDefaultData() {
        val movieDao = db.movieAppDao
        CoroutineScope(Dispatchers.IO).launch {
            movieDao.insertMovies(MoviesDataSourceDefault().getMovies())
            movieDao.insertActors(ActorsDataSourceDefault().getActors())
            movieDao.insertMovieActorCrossRefAll(MoviesDataSourceDefault().getMoviesAndActors())
        }
    }
}