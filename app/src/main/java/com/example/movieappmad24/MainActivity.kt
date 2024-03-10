package com.example.movieappmad24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import androidx.compose.material.icons.filled.Favorite


class MainActivity : ComponentActivity() {



    @Composable
    fun MovieApp() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppScaffold()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppScaffold() {
        Scaffold(
            topBar = { AppTopBar() },
            bottomBar = { AppBottomBar() }
        ) { innerPadding ->
            MovieList(movies = getMovies(), innerPaddingValues = innerPadding)
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppTopBar() {
        TopAppBar(
            title = {
                Text(
                    text = "Movie App",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        )
    }

    @Composable
    fun AppBottomBar() {
        BottomAppBar {
            NavigationBarItem()
        }
    }

    @Composable
    fun NavigationBarItem() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            NavigationBarButton(icon = Icons.Default.Home, label = "Home") {
                // Handle Home action
            }
            NavigationBarButton(icon = Icons.Default.Star, label = "Watchlist") {
                // Handle Watchlist action
            }
        }
    }

    @Composable
    fun NavigationBarButton(icon: ImageVector, label: String, onClick: () -> Unit) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = onClick) {
                Icon(imageVector = icon, contentDescription = label)
            }
            Text(text = label)
        }
    }


    @Composable
    fun MovieList(movies: List<Movie>, innerPaddingValues: PaddingValues){
        LazyColumn(contentPadding = innerPaddingValues){
            items(movies){
                movie ->
                MovieRow(movie)

            }
        }

    }

    @Composable
    fun MovieRow(movie: Movie){
        var expanded by remember { mutableStateOf(false) }
        var favorited by remember { mutableStateOf(false) }

        Card (modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
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

                            modifier = Modifier.clickable {favorited = !favorited   },
                            imageVector = if (favorited) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (favorited) "Remove from Favorites" else "Add to Favorites"
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
    fun ToggleMovieDetails(movie: Movie){
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Director: "+ movie.director)
            Text(text = "Released: "+ movie.year)
            Text(text = "Genre: " + movie.genre)
            Text(text = "Actors: "+ movie.actors)
            Text(text = "Rating: "+ movie.rating)
            Text(text = "Plot: "+ movie.plot)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MovieAppMAD24Theme {
                MovieApp()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun DefaultPreview(){
        MovieAppMAD24Theme {
            val immutableListOfStrings = listOf("Avatar", "madmax", "enchanted")
            //MovieList(movies = getMovies())

            Scaffold(
                topBar = {

                    TopAppBar(
                        title = { Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Movie App",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center, // Align text to center
                                style = MaterialTheme.typography.titleLarge
                            )
                        } },

                        )
                }
                ,bottomBar = {


                    BottomAppBar {
                        Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceEvenly)
                        {
                            Column {
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = "Home"
                                    )
                                }
                                Text(text="Home")
                            }
                            Column {
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = "Watchlist"
                                    )
                                }
                                Text(text = "Watchlist")
                            }

                        }


                    }
                }
            ){
                    innerPadding -> MovieList(movies = getMovies(), innerPaddingValues = innerPadding)

            }
        }
    }





}





