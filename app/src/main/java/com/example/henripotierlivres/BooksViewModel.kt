package com.example.henripotierlivres

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.henripotierlivres.models.BooksResponse
import com.example.henripotierlivres.models.CommercialOffer
import com.example.henripotierlivres.repository.BooksRepository
import com.example.henripotierlivres.util.Resource

import kotlinx.coroutines.launch

class BooksViewModel(
    private val henriPotierRepository: BooksRepository
) : ViewModel() {


    val books = MutableLiveData<List<BooksResponse>>()
    val commercialOffers = MutableLiveData<List<CommercialOffer>>()
    val totalCartPrice = MutableLiveData<Int>()

    init {
        getEmployees()
    }

    fun getEmployees() = viewModelScope.launch {
        books.postValue(Resource.Loading())
        val response = henriPotierRepository.getBooks()
        if (response != null) {
            books.postValue(Resource.Success(response))
        } else {
            books.postValue(Resource.Error("Error fetching books"))
        }
    }


    fun getCommercialOffer() = viewModelScope.launch {
        commercialOffers.postValue(Resource.Loading())
        val response = henriPotierRepository.getCommercialOffers()
        if (response != null) {
            commercialOffers.postValue(Resource.Success(response))
        } else {
            commercialOffers.postValue(Resource.Error("Error fetching books"))
        }
    }


    fun calculateTotalPrice(selectedBooks: List<BooksResponse>, offers: List<CommercialOffer>) {
        val totalPrice = selectedBooks.sumBy { it.price }
        val discountedPrices = offers.map { offer ->
            when (offer.type) {
                "percentage" -> totalPrice * (1 - offer.value / 100.0)
                "minus" -> totalPrice - offer.value
                "slice" -> {
                    val slices = totalPrice / (offer.sliceValue ?: 1)
                    totalPrice - slices * offer.value
                }
                else -> totalPrice.toDouble()
            }
        }
        totalCartPrice.value = discountedPrices.minOrNull()?.toInt() ?: totalPrice
    }



}