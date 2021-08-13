package ru.skillbranch.searchmovie.data.database.dao

import androidx.room.*
import ru.skillbranch.searchmovie.data.database.entities.Actor
import ru.skillbranch.searchmovie.data.database.entities.Movie
import ru.skillbranch.searchmovie.data.database.entities.MovieActorCrossRef
import ru.skillbranch.searchmovie.data.database.entities.MoviesWithActors

@Dao
interface MoviesDao {
    @Query("SELECT * FROM Movies")
    fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM Movies WHERE movie_id = :movieId")
    fun getMovieById(movieId: Int): Movie

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(actors: List<Actor>)

    @Update
    suspend fun updateMovie(movies: Movie)

    @Query("DELETE FROM Movies")
    suspend fun deleteAllMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieActorCrossRef(crossRef: MovieActorCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieActorCrossRefAll(crossRefs: List<MovieActorCrossRef>)

    /**
     * Получить фильм со списком актеров
     */
    @Transaction
    @Query("SELECT * FROM Movies")
    suspend fun getMoviesWithActors(): List<MoviesWithActors>
}