package com.example.movieappmad24.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.movieappmad24.models.Movie;
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.MovieWithImage
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert
    suspend fun insert(movie: Movie)

    @Insert
    suspend fun insertImages(images: MovieImage)

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

  /*  @Query("SELECT * FROM movie WHERE movieid LIKE :search")
    fun findMovieByIdFromDB (search:String)


    @Query("SELECT * FROM movie")
    fun getAllMoviesFromDB()

    @Query("SELECT * FROM movie WHERE is_favorite=:favorite")
    fun getAllFavoriteMovies(favorite : Boolean = true)*/
    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    //fun insertAll(movies: List<Movie>)

    @Transaction
    @Query("SELECT url from images where movieId = :movieId")
    fun getImages(movieId: String): Flow<List<String>>
    @Transaction
    @Query("SELECT * FROM movies WHERE dbId = :movieId")
    fun getMovieWithImages(movieId: Long): Flow<MovieWithImage>

   @Transaction
   @Query("SELECT * FROM movies")
    fun getAllMoviesWithImage() : Flow<List<MovieWithImage>>

    @Transaction
    @Query("SELECT * from movies where isfavoriteDB= 1")
    fun getFavorite(): Flow<List<MovieWithImage>>

}