package com.example.henripotierlivres.repository

import com.example.henripotierlivres.api.BooksApi
import com.example.henripotierlivres.models.BooksResponse
import com.example.henripotierlivres.models.CommercialOffer
import com.example.henripotierlivres.models.Offer
import retrofit2.Call


class BooksRepository(private val apiBooks: BooksApi) {

    suspend fun getBooks(): List<BooksResponse> {
        return apiBooks.getBooks()
    }

    suspend fun getCommercialOffers(selectedIsbnList: List<String>): List<CommercialOffer> {
        val isbns = selectedIsbnList.joinToString(",")
        return apiBooks.getCommercialOffers(isbns)
    }
}