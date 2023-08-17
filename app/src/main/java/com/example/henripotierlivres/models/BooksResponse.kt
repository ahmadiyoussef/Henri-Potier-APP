package com.example.henripotierlivres.models

import com.google.gson.annotations.SerializedName

data class BooksResponse(
    @SerializedName("cover")
    val cover: String,
    @SerializedName("isbn")
    val isbn: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("synopsis")
    val synopsis: List<String>,
    @SerializedName("title")
    val title: String,
    var isSelected: Boolean
)