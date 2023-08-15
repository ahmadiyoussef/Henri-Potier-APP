package com.example.henripotierlivres.api

import com.example.henripotierlivres.models.BooksResponse
import retrofit2.Call

import retrofit2.http.GET

interface HenriPotierApi {

    @GET("books")
    suspend fun getBooks(
    ): Call<List<BooksResponse>>


}
