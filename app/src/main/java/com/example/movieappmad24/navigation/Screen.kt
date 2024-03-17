package com.example.movieappmad24.navigation

sealed class Screen(val route:String) {
    data object Home : Screen(route = "homescreen")
    data object Detail : Screen(route = "detailscreen/{movieId}") {
        fun createRoute(movieId: String) = "detailscreen/$movieId"
    }
    object Watchlist : Screen(route= "watchlist")
}