package com.example.movieappmad24.dependencyinjection

import android.content.Context
import com.example.movieappmad24.database.AppDatabase
import com.example.movieappmad24.repository.MovieRepository
import com.example.movieappmad24.viewmodels.MovieViewModelFactory

object InjectorUtils {

    private fun getMovieRepository(context: Context) :MovieRepository {
        return MovieRepository.getInstance(AppDatabase.getDatabase(context.applicationContext).movieDao())
    }
    fun provideMovieViewModelFactory(context: Context): MovieViewModelFactory{
        val repository = getMovieRepository(context)
        return MovieViewModelFactory(repository)
    }
}