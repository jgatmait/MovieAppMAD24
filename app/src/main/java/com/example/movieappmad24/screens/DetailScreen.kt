

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieappmad24.dependencyinjection.InjectorUtils
//import com.example.movieappmad24.models.getMovieFromId

import com.example.movieappmad24.ui.components.HorizontalScrollableImageView
import com.example.movieappmad24.ui.components.MovieRow
import com.example.movieappmad24.ui.components.SimpleTopAppBar

import com.example.movieappmad24.viewmodels.DetailViewModel
import com.example.movieappmad24.ui.components.MovieTrailerPlayer


//import com.example.movieappmad24.ui.components.MovieImagesRow


@Composable
fun DetailScreen(
    movieId: String?,
    navController: NavController,

) {
    val detailViewModel: DetailViewModel = viewModel(factory = InjectorUtils.provideMovieViewModelFactory(context = LocalContext.current))
    movieId?.let {
        LaunchedEffect(movieId) {
        detailViewModel.getMovieById(movieId)
    }

        val movieState by detailViewModel.movie.collectAsState()
        movieState?.let { movieWithImage ->
            //val movie = getMovies().filter { movie -> movie.id == movieId }[0]

        Scaffold (
            topBar = {
                SimpleTopAppBar(title = movieWithImage.movie.title) {
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
                    movieWithImage = movieWithImage,
                    onFavoriteClick = {
                        detailViewModel.updateFavorite(movieWithImage)
                    }
                )

                HorizontalDivider(modifier = Modifier.padding(6.dp))

                MovieTrailerPlayer(movie = movieWithImage.movie)


                HorizontalScrollableImageView(movieWithImage = movieWithImage)
            }
        }
    }
}}



