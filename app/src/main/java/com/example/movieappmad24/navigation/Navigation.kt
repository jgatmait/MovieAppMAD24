
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappmad24.HomeScreen
import com.example.movieappmad24.screens.WatchlistScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "homescreen") {
        composable(route = "homescreen") {
            HomeScreen(navController = navController)
        }
        composable("detailscreen/{movieId}",
            arguments = listOf(navArgument(name="movieId"){type= NavType.StringType})
        ) {
            //DetailScreen(navController = navController)
                backStackEntry->
            val movieId = backStackEntry.arguments?.getString("movieId")
            DetailScreen(navController, movieId)

            //DetailScreen(movieId = backStackEntry.arguments?.getString("movieId"))
        }
        composable("watchlistscreen"){
            WatchlistScreen(navController)
        }
    }
}
