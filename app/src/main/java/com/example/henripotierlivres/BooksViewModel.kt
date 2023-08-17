package com.example.henripotierlivres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.henripotierlivres.models.BooksResponse
import com.example.henripotierlivres.models.CommercialOffer
import com.example.henripotierlivres.models.Offer
import com.example.henripotierlivres.repository.BooksRepository
import com.example.henripotierlivres.util.Resource

import kotlinx.coroutines.launch
import retrofit2.Call

class BooksViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {


    val books = MutableLiveData<Resource<List<BooksResponse>>>()
    val commercialOffers = MutableLiveData<Resource<List<CommercialOffer>>>()
    val totalCartPrice = MutableLiveData<Int>()


    init {
        getBooks()
    }

    fun getBooks() = viewModelScope.launch {
        books.postValue(Resource.Loading())
        val response = booksRepository.getBooks()
        if (response != null) {
            books.postValue(Resource.Success(response))
        } else {
            books.postValue(Resource.Error("Error fetching books"))
        }
    }


    fun getCommercialOffer(selectedIsbnList: List<String>) = viewModelScope.launch {
        commercialOffers.postValue(Resource.Loading())
        val response = booksRepository.getCommercialOffers(selectedIsbnList)
        if (response != null) {
            commercialOffers.postValue(Resource.Success(response))
        } else {
            commercialOffers.postValue(Resource.Error("Error fetching books"))
        }
    }


    fun calculateTotalPrice(selectedBooks: List<BooksResponse>, offers: List<CommercialOffer>) {
        val originalTotalPrice = selectedBooks.sumOf { it.price }
        val discountedPrices = offers.map { offer ->
            when (offer.type) {
                "percentage" -> originalTotalPrice * (100 - offer.value) / 100
                "minus" -> originalTotalPrice - offer.value
                "slice" -> {
                    val numberOfSlices = originalTotalPrice / offer.sliceValue!!
                    originalTotalPrice - (numberOfSlices * offer.value)
                }

                else -> originalTotalPrice
            }
        }
        discountedPrices.minOrNull() ?: originalTotalPrice
    }


}