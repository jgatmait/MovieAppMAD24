

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieappmad24.MovieList
import com.example.movieappmad24.models.getMovies

@Composable
fun DetailScreen(navController: NavController, movieId: String?){
    Scaffold(
        topBar = { AppTopBar(movieId) },
        bottomBar = { AppBottomBar(navController) }
    ) { innerPadding ->
        Text(modifier = Modifier.padding(innerPadding), text = "Details of movie ID: $movieId" )
    }


}

/*@Composable
fun AppScaffold(navController: NavController, movieId: String?) {
    Scaffold(
        topBar = { AppTopBar(movieId) },
        bottomBar = { AppBottomBar() }
    ) { innerPadding ->
        MovieList(navController, movies = getMovies(), innerPaddingValues = innerPadding)
    }
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(movieId: String?) {
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.primary,
    ),
        title = {
            if (movieId != null) {
                Text(
                    text = movieId,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    )
}

@Composable
fun AppBottomBar(navController: NavController) {
    BottomAppBar (
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    )  {
        NavigationBarItem(navController)
    }
}

@Composable
fun NavigationBarItem(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        NavigationBarButton(navController, icon = Icons.Default.Home, label = "Home" ) {
            // Handle Home action
            navController.popBackStack()
             }
        NavigationBarButton(navController, icon = Icons.Default.Star, label = "Watchlist") {
            // Handle Watchlist action
            navController.navigate("watchlistscreen")
        }
    }
}

@Composable
fun NavigationBarButton(navController: NavController, icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = onClick ) {
            Icon(imageVector = icon, contentDescription = label)
        }
        Text(text = label)
    }
}

