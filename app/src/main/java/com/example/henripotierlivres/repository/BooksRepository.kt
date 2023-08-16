package com.example.henripotierlivres.repository

import com.example.henripotierlivres.api.BooksApi
import com.example.henripotierlivres.models.BooksResponse
import com.example.henripotierlivres.models.CommercialOffer


class BooksRepository(private val apiBooks: BooksApi) {

    suspend fun getBooks(): List<BooksResponse> {
        return apiBooks.getBooks()
    }

    suspend fun getCommercialOffers(isbnList: String): List<CommercialOffer> {
        return apiBooks.getCommercialOffers(isbnList)
    }
}