package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImage
import com.example.movieappmad24.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel (private val repository: MovieRepository) : ViewModel() {
    private val _movie = MutableStateFlow<MovieWithImage?>(null)
    val movie: StateFlow<MovieWithImage?> = _movie.asStateFlow()

    fun getMovieById(movieId: String) {
        val id: Long? = movieId.toLongOrNull()
        if (id != null) {
            viewModelScope.launch {
                repository.getMovieById(id).collect { movie ->
                    _movie.value = movie
                }
            }
        }
    }



    fun updateFavorite(movie: MovieWithImage) {
        movie.movie.isFavoriteDB = !movie.movie.isFavoriteDB
        viewModelScope.launch {
            repository.updateMovie(movie.movie)
        }
    }
}