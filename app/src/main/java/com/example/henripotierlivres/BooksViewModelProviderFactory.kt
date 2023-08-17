package com.example.henripotierlivres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.henripotierlivres.repository.BooksRepository

class BooksViewModelProviderFactory(
    private val repository: BooksRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BooksViewModel(repository) as T
    }
}

