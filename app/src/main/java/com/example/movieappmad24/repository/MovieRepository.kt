package com.example.movieappmad24.repository

import com.example.movieappmad24.dao.MovieDao
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.MovieWithImage
import kotlinx.coroutines.flow.Flow

class MovieRepository (private val movieDao: MovieDao) {

    suspend fun insertMovie(movie: Movie) = movieDao.insert(movie)
    suspend fun updateMovie(movie: Movie) = movieDao.update(movie)
    suspend fun deleteMovie(movie: Movie) = movieDao.delete(movie)
    suspend fun insertMovieImages(movieImage: MovieImage) = movieDao.insertImages(movieImage)

    suspend fun getImage(movie: Movie) = movieDao.getImages(movie.id)

    suspend fun getAllMovies(): Flow<List<MovieWithImage>> = movieDao.getAllMoviesWithImage()
    suspend fun getFavoriteMovies(): Flow<List<MovieWithImage>> = movieDao.getFavorite()
    suspend fun getMovieById(movieId: Long): Flow<MovieWithImage?> = movieDao.getMovieWithImages(movieId)

    companion object {
        @Volatile
        private var Instance: MovieRepository? = null

        fun getInstance(dao: MovieDao) = Instance ?: synchronized(lock = this) {
            Instance ?: MovieRepository(dao)
                .also { Instance = it }
        }
    }
}