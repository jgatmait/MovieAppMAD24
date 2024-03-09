package com.example.movieappmad24

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import org.w3c.dom.Text
import androidx.compose.foundation.Image


class MainActivity : ComponentActivity() {

    @Composable
    fun MovieRow(movie: Movie){
        var expanded by remember { mutableStateOf(false) }

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
                            tint = MaterialTheme.colorScheme.secondary,
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Add to favorites"
                        )
                    }

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = movie.title)
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
    fun MovieList(movies: List<Movie>, innerPaddingValues: PaddingValues){
        LazyColumn(contentPadding = innerPaddingValues){
            items(movies){
                movie ->
                MovieRow(movie)

            }
        }

    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FirstTopAppBar(){
        val context  = LocalContext.current.applicationContext
        Scaffold(

            topBar = {TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text("Movie App")
            }
        )}) {
            innerPadding -> Text(
            modifier = Modifier.padding(innerPadding),
            text = "Example of a scaffold with a top bar."
        )
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MovieAppMAD24Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,

                )
                {

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
                                Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceAround)
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
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
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



                    //MovieRow()
                    val immutableListOfStrings = listOf("String1", "String2", "String3")
                    //MovieList(movies = getMovies())
                    /*Card {
                        Image(painter = painterResource(id = R.drawable.movie_image), contentDescription = "placeholder_image")
                    }*/
                }
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

    @Preview
    @Composable
    fun TopBarPreview(){
        MovieAppMAD24Theme {
            FirstTopAppBar()
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



}





