package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieappmad24.repository.MovieRepository

class MovieViewModelFactory(private val movieRepository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(movieRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return DetailViewModel(movieRepository) as T
            }
            modelClass.isAssignableFrom(WatchlistViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return WatchlistViewModel(movieRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
