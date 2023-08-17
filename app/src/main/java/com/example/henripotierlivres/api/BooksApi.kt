package com.example.henripotierlivres.api

import com.example.henripotierlivres.models.BooksResponse
import com.example.henripotierlivres.models.CommercialOffer
import com.example.henripotierlivres.models.Offer
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Path

interface BooksApi {

    @GET("books")
    suspend fun getBooks(
    ): List<BooksResponse>

    @GET("books/{isbns}/commercialOffers")
    suspend fun getCommercialOffers(@Path("isbns") selectedIsbnList: List<String>): List<CommercialOffer>
}
