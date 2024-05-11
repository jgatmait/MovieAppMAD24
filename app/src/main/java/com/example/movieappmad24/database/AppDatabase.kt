package com.example.movieappmad24.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.movieappmad24.dao.MovieDao
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.workers.WorkManagerDBRepo

@Database(
    entities = [Movie::class, MovieImage::class],
    version = 6,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    companion object{
        @Volatile
        private var instance: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase{
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "movie_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback = seedDatabase(context = context))
                    .build()
                    .also {
                        instance = it
                    }
            }
        }
        private fun seedDatabase(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    WorkManagerDBRepo(context).seedDatabase()
                }
            }
        }
    }
}