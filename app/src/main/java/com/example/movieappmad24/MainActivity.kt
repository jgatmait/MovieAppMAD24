package com.example.movieappmad24

import Navigation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import com.example.movieappmad24.viewmodels.MovieViewModel
import com.example.movieappmad24.viewmodels.MovieViewModelFactory


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppMAD24Theme {
                Navigation()
            }
        }
    }

}


/*
    @Composable
    fun MovieApp() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppScaffold()
        }
    }


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
        TopAppBar(colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
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
        BottomAppBar (
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary,
        )  {
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
        var favorite by remember { mutableStateOf(false) }

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

*/














