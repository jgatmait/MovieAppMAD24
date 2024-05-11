package com.example.movieappmad24.viewmodels

import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImage
import kotlinx.coroutines.flow.StateFlow

interface MovieViewModel {
    fun updateFavorite(movie: MovieWithImage)

    val movies: StateFlow<List<MovieWithImage>>
}