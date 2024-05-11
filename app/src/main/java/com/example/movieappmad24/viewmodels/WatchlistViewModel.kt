package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.models.MovieWithImage
import com.example.movieappmad24.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class WatchlistViewModel(private val repository: MovieRepository) : ViewModel(), MovieViewModel {
    private val _movies = MutableStateFlow(listOf<MovieWithImage>())
    override var movies = _movies.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getFavoriteMovies().distinctUntilChanged()
                .collect{ listOfMovies ->
                    _movies.value = listOfMovies
                }
        }
    }
    override fun updateFavorite(movie: MovieWithImage) {
        movie.movie.isFavoriteDB = !movie.movie.isFavoriteDB
        viewModelScope.launch {
            repository.updateMovie(movie.movie)
        }
    }

}