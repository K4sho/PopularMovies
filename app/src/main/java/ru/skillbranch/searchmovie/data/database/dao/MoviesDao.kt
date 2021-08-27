package ru.skillbranch.searchmovie.data.database.dao

import androidx.room.*
import ru.skillbranch.searchmovie.data.database.entities.Actor
import ru.skillbranch.searchmovie.data.database.entities.Movie
import ru.skillbranch.searchmovie.data.database.entities.MovieActorCrossRef
import ru.skillbranch.searchmovie.data.database.entities.MoviesWithActors

@Dao
interface MoviesDao {
    @Query("SELECT * FROM Movies")
    suspend fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM Movies WHERE movie_id = :movieId")
    fun getMovieById(movieId: Int): Movie

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movies: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(actors: List<Actor>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMovies(movies: List<Movie>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateActors(actors: List<Actor>)

    @Query("DELETE FROM Movies")
    suspend fun deleteAllMovies()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieActorCrossRef(crossRef: MovieActorCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieActorCrossRefAll(crossRefs: List<MovieActorCrossRef>)

    /**
     * Получить фильмы со списком актеров
     */
    @Transaction
    @Query("SELECT * FROM Movies")
    suspend fun getMoviesWithActors(): List<MoviesWithActors>

    @Transaction
    @Query("SELECT * FROM Movies WHERE movie_id = :idMovie ")
    suspend fun getMovieWithActors(idMovie: Int) : MoviesWithActors

    // Получить кол-во фильмов
    @Query("SELECT COUNT(movie_id) FROM Movies WHERE movie_id > 0")
    suspend fun getCount(): Int
}