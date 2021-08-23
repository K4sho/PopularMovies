package ru.skillbranch.searchmovie.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.skillbranch.searchmovie.data.database.dao.MoviesDao
import ru.skillbranch.searchmovie.data.database.dao.ProfileDao
import ru.skillbranch.searchmovie.data.database.entities.Actor
import ru.skillbranch.searchmovie.data.database.entities.Movie
import ru.skillbranch.searchmovie.data.database.entities.MovieActorCrossRef
import ru.skillbranch.searchmovie.data.database.entities.Profile
import ru.skillbranch.searchmovie.data.database.typeconvertes.ListStringConverter
import ru.skillbranch.searchmovie.data.sources.movies.ActorsDataSourceDefault
import ru.skillbranch.searchmovie.data.sources.movies.MoviesDataSourceDefault

@Database(
    entities = [
        Movie::class,
        Actor::class,
        MovieActorCrossRef::class,
        Profile::class
    ],
    version = 6,
    exportSchema = false
)
@TypeConverters(ListStringConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieAppDao(): MoviesDao
    abstract fun userDao(): ProfileDao

    companion object {
        @Volatile
        private var instance: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            synchronized(this) {
                return instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_db.db"
                ).addCallback(roomCallback).fallbackToDestructiveMigration().build().also {
                    instance = it
                }
            }
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                prepareDatabase(instance!!)
            }
        }

        /// Загрузка в базу статических данны
        /// TODO: Позже будет из API
        private fun prepareDatabase(db: MovieDatabase) {
            val movieDao = db.movieAppDao()
            CoroutineScope(Dispatchers.IO).launch {
                movieDao.insertMovies(MoviesDataSourceDefault().getMovies())
                movieDao.insertActors(ActorsDataSourceDefault().getActors())
                movieDao.insertMovieActorCrossRefAll(MoviesDataSourceDefault().getMoviesAndActors())
            }
        }
    }

    suspend fun isEmpty() : Boolean {
        return  movieAppDao().getCount() == 0
    }
}