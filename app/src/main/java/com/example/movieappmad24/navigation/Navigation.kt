
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappmad24.HomeScreen
import com.example.movieappmad24.navigation.Screen
import com.example.movieappmad24.screens.WatchlistScreen
import com.example.movieappmad24.models.MoviesViewModel
import com.example.movieappmad24.navigation.DETAIL_ARGUMENT_KEY


@Composable
fun Navigation() {
    val navController = rememberNavController()
    val moviesViewModel: MoviesViewModel = viewModel()  // create a MoviesViewModel instance to use in HomeScreen and WatchlistScreen

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController, moviesViewModel = moviesViewModel)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument(name = DETAIL_ARGUMENT_KEY) {type = NavType.StringType})
        ) {
            //DetailScreen(navController = navController)
                backStackEntry->
            val movieId = backStackEntry.arguments?.getString("movieId")
            DetailScreen(
                navController = navController,
                movieId = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY),
                moviesViewModel = moviesViewModel)

            //DetailScreen(movieId = backStackEntry.arguments?.getString("movieId"))
        }
        composable(Screen.Watchlist.route){
            WatchlistScreen(navController, moviesViewModel = moviesViewModel)
        }
    }
}
