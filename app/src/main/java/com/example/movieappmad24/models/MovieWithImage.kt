package com.example.movieappmad24.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation



data class MovieWithImage(
    @Embedded val movie: Movie,
    @Relation(
        parentColumn = "dbId",
        entityColumn = "id"
    )
    val images: List<MovieImage>
)