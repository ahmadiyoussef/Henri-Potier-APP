package com.example.henripotierlivres.models

data class BooksResponse(
    val cover: String,
    val isbn: String,
    val price: Int,
    val synopsis: List<String>,
    val title: String
)