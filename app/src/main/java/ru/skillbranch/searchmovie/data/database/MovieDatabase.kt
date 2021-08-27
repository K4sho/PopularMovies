package ru.skillbranch.searchmovie.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.skillbranch.searchmovie.data.database.dao.MoviesDao
import ru.skillbranch.searchmovie.data.database.dao.ProfileDao
import ru.skillbranch.searchmovie.data.database.entities.Actor
import ru.skillbranch.searchmovie.data.database.entities.Movie
import ru.skillbranch.searchmovie.data.database.entities.MovieActorCrossRef
import ru.skillbranch.searchmovie.data.database.entities.Profile
import ru.skillbranch.searchmovie.data.database.typeconvertes.ListIntConverter
import ru.skillbranch.searchmovie.data.database.typeconvertes.ListStringConverter

@Database(
    entities = [
        Movie::class,
        Actor::class,
        MovieActorCrossRef::class,
        Profile::class
    ],
    version = 10,
    exportSchema = false
)
@TypeConverters(ListStringConverter::class, ListIntConverter::class)
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
                ).fallbackToDestructiveMigration().build().also {
                    instance = it
                }
            }
        }
    }

    suspend fun isEmpty(): Boolean {
        return movieAppDao().getCount() == 0
    }
}