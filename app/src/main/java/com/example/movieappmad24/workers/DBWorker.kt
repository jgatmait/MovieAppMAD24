package com.example.movieappmad24.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.movieappmad24.database.AppDatabase
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.repository.MovieRepository

private const val TAG = "DatabaseWorker"
class DBWorker (ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
    private val dao = AppDatabase.getDatabase(context = ctx).movieDao()
    private val repository = MovieRepository(movieDao = dao)
    override suspend fun doWork(): Result {
        return try {
            var movieId: Long = 1
            val movies = getMovies()
            movies.forEach{
                repository.insertMovie(it)
                it.images.forEach{url ->
                    repository.insertMovieImages(MovieImage(movieId = movieId, url = url))
                }
                movieId += 1
            }
            Result.success()
        } catch (throwable: Throwable) {
            Result.failure()
        }
    }
}