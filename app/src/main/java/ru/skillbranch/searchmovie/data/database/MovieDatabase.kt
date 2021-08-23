package ru.skillbranch.searchmovie.data.database

import android.content.Context
import android.util.Log
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
    abstract val movieAppDao: MoviesDao
    abstract val userDao: ProfileDao

    companion object {
        @Volatile
        private var instance: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            synchronized(this) {
                return instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_db.db"
                ).fallbackToDestructiveMigration().build().also {
                    instance = it
                }
            }
        }
    }
}