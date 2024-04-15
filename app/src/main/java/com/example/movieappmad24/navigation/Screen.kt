package com.example.movieappmad24.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

/*
sealed class Screen(val route:String, val icon: ImageVector) {
    data object Home : Screen(route = "homescreen", Icons.Default.Home)
    data object Detail : Screen(route = "detailscreen/{movieId}" ,Icons.Default.ArrowBack ) {
        fun createRoute(movieId: String) = "detailscreen/$movieId"
    }
    data object Watchlist : Screen(route = "watchlist", Icons.Default.Star)
}*/



const val DETAIL_ARGUMENT_KEY = "movieId"
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{$DETAIL_ARGUMENT_KEY}") {
        fun withId(id: String): String {
            return this.route.replace(oldValue = "{$DETAIL_ARGUMENT_KEY}", newValue = id)
        }
    }
    object Watchlist : Screen("watchlist")
}