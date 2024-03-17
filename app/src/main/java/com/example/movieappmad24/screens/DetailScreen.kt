

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovieFromId

@Composable
fun DetailScreen(navController: NavController, movieId: String?){
    Scaffold(
        topBar = { AppTopBar(navController, movieId) },
        bottomBar = { AppBottomBar(navController) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)){
            MovieRow(navController , movie = getMovieFromId(movieId))
            MovieImagesRow(movie = getMovieFromId(movieId))
        }



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
fun AppTopBar(navController: NavController, movieId: String?) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                IconButton(onClick = {navController.popBackStack()},) {
                    Icon(Icons.Default.ArrowBack , contentDescription ="Back")

                }

                if (movieId != null) {
                    Text(
                        text = getMovieFromId(movieId).title,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

            }

        },

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

@Composable
fun MovieRow( navController: NavController, movie: Movie){
    var expanded by remember { mutableStateOf(false) }
    var favorite by remember { mutableStateOf(false) }

    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(2.dp),
        shape = ShapeDefaults.Large,
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentAlignment = Alignment.Center
            )
            {
                Image(
                    //painter = painterResource(id = R.drawable.movie_image),
                    painter = rememberImagePainter(data =movie.images.first(), builder=  {
                        crossfade(true)
                    }),
                    contentDescription = movie.title+" image",
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        //tint = MaterialTheme.colorScheme.secondary,

                        modifier = Modifier.clickable {favorite = !favorite   },
                        imageVector = if (favorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (favorite) "Remove from Favorites" else "Add to Favorites"
                    )
                }

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = movie.title, style=MaterialTheme.typography.titleMedium)
                Icon(modifier = Modifier.clickable {expanded = !expanded   },
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand")

            }


        }
        AnimatedVisibility(visible = expanded) {
            ToggleMovieDetails(movie = movie)
        }

    }

}

@Composable
fun MovieImagesRow( movie: Movie){
    LazyRow(Modifier.padding(10.dp)) {
        val images = movie.images
        items(images){
            image ->
            Image(
                painter = rememberImagePainter(data = image, builder = {
                    crossfade(true)
                }),
                contentDescription = "pic",
                contentScale = ContentScale.Fit, // Adjust as necessary
                modifier = Modifier.width(300.dp).height(200.dp).padding(5.dp) // Example fixed size, adjust as necessary

            )
        }

    }

}
@Composable
fun ToggleMovieDetails(movie: Movie){
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Director: "+ movie.director)
        Text(text = "Released: "+ movie.year)
        Text(text = "Genre: " + movie.genre)
        Text(text = "Actors: "+ movie.actors)
        Text(text = "Rating: "+ movie.rating)
        Divider(modifier = Modifier.padding(10.dp))
        Text(text = "Plot: "+ movie.plot)
    }
}
