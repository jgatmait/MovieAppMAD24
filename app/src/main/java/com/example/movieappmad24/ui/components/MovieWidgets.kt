package com.example.movieappmad24.ui.components

import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.navigation.Screen
import com.example.movieappmad24.models.MoviesViewModel
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView



//code template taken from Leon's branch lecture 04

@Composable
fun MovieList(
    modifier: Modifier,
    movies: List<Movie>,
    navController: NavController,
    moviesViewModel: MoviesViewModel

){
    LazyColumn  (modifier){
        items(movies) { movie ->
            MovieRow(
                movie = movie,
                //modifier = modifier,
                onFavoriteClick = { movieId ->
                    moviesViewModel.toggleFavoriteMovie(movieId)
                },
                onItemClick = { movieId ->
                    navController.navigate(route = Screen.Detail.withId(movieId))
                }
            )
        }
    }
}

@Composable
fun MovieRow(
    //modifier: Modifier, //uncomment, this is messing up padding
    movie: Movie,
    onFavoriteClick: (String) -> Unit = {},
    onItemClick: (String) -> Unit = {}
){
    Card(modifier = Modifier // it's probably not good practice to use a variable name with just lowercase of the class it is using.. change M to m and all the padding is messed up
        .fillMaxWidth()
        .padding(vertical = 2.dp, horizontal = 5.dp)
        .clickable {
            onItemClick(movie.id)
        },
        shape = ShapeDefaults.Large,
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column {

            MovieCardHeader(
                imageUrl = movie.images[0],
                isFavorite = movie.isFavorite,
                onFavoriteClick = { onFavoriteClick(movie.id) }
            )

            MovieDetails(modifier = Modifier.padding(12.dp), movie = movie)

        }
    }
}


@Composable
fun MovieCardHeader(
    imageUrl: String,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        MovieImage(imageUrl)

        FavoriteIcon(isFavorite = isFavorite, onFavoriteClick)
    }
}

@Composable
fun MovieImage(imageUrl: String){
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop,
        contentDescription = "movie poster",
        loading = {
            CircularProgressIndicator()
        }
    )
}

@Composable
fun FavoriteIcon(
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.TopEnd
    ){
        Icon(
            modifier = Modifier.clickable {
                onFavoriteClick() },
            tint = Color.Red,
            imageVector =
            if (isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },

            contentDescription = "Add to favorites")
    }
}


@Composable
fun MovieDetails(modifier: Modifier, movie: Movie) {
    var showDetails by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = movie.title)
        Icon(modifier = Modifier
            .clickable {
                showDetails = !showDetails
            },
            imageVector =
            if (showDetails) Icons.Filled.KeyboardArrowDown
            else Icons.Default.KeyboardArrowUp, contentDescription = "show more")
    }


    AnimatedVisibility(
        visible = showDetails,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column (modifier = modifier) {
            Text(text = "Director: ${movie.director}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Released: ${movie.year}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Genre: ${movie.genre}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Actors: ${movie.actors}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Rating: ${movie.rating}", style = MaterialTheme.typography.bodySmall)

            Divider(modifier = Modifier.padding(3.dp))

            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.DarkGray, fontSize = 13.sp)) {
                    append("Plot: ")
                }
                withStyle(style = SpanStyle(color = Color.DarkGray, fontSize = 13.sp, fontWeight = FontWeight.Normal)){
                    append(movie.plot)
                }
            })
        }
    }
}


@Composable
fun HorizontalScrollableImageView(movie: Movie) {
    LazyRow {
        items(movie.images) { image ->
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .size(240.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Movie poster",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}


@OptIn(UnstableApi::class) @Composable
fun MovieTrailerPlayer(movie: Movie) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri("android.resource://${context.packageName}/${movie.trailer}")
            setMediaItem(mediaItem)
            prepare()
        }
    }

    DisposableEffect(lifecycleOwner) {
        val lifecycle = lifecycleOwner.lifecycle
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    Log.e("On_resume" , "resumed")
                    exoPlayer.play() }
                Lifecycle.Event.ON_PAUSE -> {
                    Log.e("On_pause" , "paused")
                    exoPlayer.pause()}
                Lifecycle.Event.ON_STOP -> {Log.e("On_stop" , "stopped")
                    exoPlayer.stop()}
                Lifecycle.Event.ON_DESTROY -> {Log.e("On_destroy" , "destroy")
                    exoPlayer.release()}
                else -> {}
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = {
            PlayerView(context).also { playerView ->
                playerView.player = exoPlayer
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 10f)
    )
}
