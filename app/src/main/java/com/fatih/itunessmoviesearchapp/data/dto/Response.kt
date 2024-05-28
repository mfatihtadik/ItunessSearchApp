package com.fatih.itunessmoviesearchapp.data.dto

data class Response(
    val resultCount: Int?,
    val results: List<Movie>
)
data class Movie(
    val collectionName : String,
    val longDescription : String,
    val artworkUrl100 : String,
    val previewUrl : String
)
