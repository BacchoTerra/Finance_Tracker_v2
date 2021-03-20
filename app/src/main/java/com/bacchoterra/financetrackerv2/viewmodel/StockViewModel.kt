package com.bacchoterra.financetrackerv2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bacchoterra.financetrackerv2.model.Stock
import com.bacchoterra.financetrackerv2.repository.StockRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class StockViewModel(val repo:StockRepository):ViewModel(){

    val allStock = repo.allStock.asLiveData()

    fun insert(stock: Stock) = viewModelScope.launch {

        repo.insert(stock)

    }


    class StockViewModelFactory (private val repo:StockRepository): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StockViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return StockViewModel(repo) as T
            }

            throw IllegalArgumentException("Unknown viewModel class")
        }

    }


}