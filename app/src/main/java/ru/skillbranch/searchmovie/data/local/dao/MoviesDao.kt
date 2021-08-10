package ru.skillbranch.searchmovie.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.skillbranch.searchmovie.data.local.entities.Movie
import ru.skillbranch.searchmovie.data.local.entities.MoviesAndGenres

@Dao
interface MoviesDao {
    @Query("SELECT * FROM Movies")
    fun getMoviesList(): LiveData<List<Movie>>

    @Query(
        "SELECT Movies.title, Movies.description, Movies.rate_score, Movies.age_restriction" +
                ", Movies.image_url, Movies.release_date, Genres.genre_title, Movies.actor_id FROM " +
                "Movies, Genres WHERE Movies.genre_id = Genres.id"
    )
    fun getMovieWIthGenre(): LiveData<List<MoviesAndGenres>>

    @Query("SELECT * FROM Movies WHERE id = :movieId")
    fun getMovieById(movieId: Int): LiveData<Movie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movies: Movie)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDefault(movies: Movie)

    @Update
    suspend fun updateMovie(movies: Movie)

    @Query("DELETE FROM Movies")
    suspend fun deleteAllMovies()
}