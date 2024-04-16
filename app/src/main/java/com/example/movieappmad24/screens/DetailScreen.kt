

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MoviesViewModel
import com.example.movieappmad24.models.getMovieFromId
import com.example.movieappmad24.models.getMovies

import com.example.movieappmad24.ui.components.HorizontalScrollableImageView
import com.example.movieappmad24.ui.components.MovieRow
import com.example.movieappmad24.ui.components.SimpleTopAppBar

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.exoplayer.ExoPlayer
import com.example.movieappmad24.ui.components.MovieTrailerPlayer


//import com.example.movieappmad24.ui.components.MovieImagesRow


@Composable
fun DetailScreen(
    movieId: String?,
    navController: NavController,
    moviesViewModel: MoviesViewModel
) {

    movieId?.let {
        val movie = getMovies().filter { movie -> movie.id == movieId }[0]

        Scaffold (
            topBar = {
                SimpleTopAppBar(title = movie.title) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                }
            }
        ){ innerPadding ->
            Column (modifier = Modifier.padding(innerPadding)){
                MovieRow(
                    //modifier = Modifier.padding(innerPadding),
                    movie = movie,
                    onFavoriteClick = { movieId ->
                    moviesViewModel.toggleFavoriteMovie(movieId) },
                    onItemClick = {})



                MovieTrailerPlayer(movie = movie)


                HorizontalScrollableImageView(movie = movie)
            }
        }
    }
}



