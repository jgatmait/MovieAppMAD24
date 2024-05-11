package com.example.movieappmad24

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieappmad24.dependencyinjection.InjectorUtils

import com.example.movieappmad24.ui.components.MovieList
import com.example.movieappmad24.ui.components.SimpleBottomAppBar
import com.example.movieappmad24.ui.components.SimpleTopAppBar
import com.example.movieappmad24.viewmodels.HomeViewModel
import com.example.movieappmad24.viewmodels.WatchlistViewModel


@Composable
fun HomeScreen(
    navController: NavController,

) {
    val moviesViewModel: HomeViewModel = viewModel(factory = InjectorUtils.provideMovieViewModelFactory(context = LocalContext.current))
    Scaffold (
        topBar = {
            SimpleTopAppBar(title = "Movie App")
        },
        bottomBar = {
            SimpleBottomAppBar(
                navController = navController
            )
        }
    ){ innerPadding ->
        MovieList(
            modifier = Modifier.padding(innerPadding),
            movies = moviesViewModel.movies.collectAsState().value,
            navController = navController,
            moviesViewModel = viewModel(factory = InjectorUtils.provideMovieViewModelFactory(context = LocalContext.current))
        )
    }
}


