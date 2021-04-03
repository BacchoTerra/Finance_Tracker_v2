package com.bacchoterra.financetrackerv2.viewmodel

import androidx.lifecycle.*
import com.bacchoterra.financetrackerv2.model.Stock
import com.bacchoterra.financetrackerv2.repository.StockRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class StockViewModel(private val repo: StockRepository) : ViewModel() {

    companion object {

        const val ORDER_BY_DESC = 1
        const val ORDER_BY_ASC = 2
        const val FINISHED = 3
        const val UNFINISHED = 4

    }


    fun insert(stock: Stock) = viewModelScope.launch {
        repo.insert(stock)
    }

    fun update(stock: Stock) = viewModelScope.launch {
        repo.update(stock)
    }

    fun delete(stock: Stock) = viewModelScope.launch {
        repo.delete(stock)
    }

    fun deleteAll() = viewModelScope.launch {
        repo.deleteAll()
    }

    fun selectAllStockWithFilter(filter: Int): LiveData<List<Stock>> {

        return when (filter) {

            ORDER_BY_DESC ->  repo.selectAllOrderByDesc().asLiveData()
            ORDER_BY_ASC ->  repo.selectAllOrderByAsc().asLiveData()
            FINISHED ->  repo.selectAllFinished().asLiveData()
            UNFINISHED ->  repo.selectAllUnfinished().asLiveData()
            else -> repo.selectAll().asLiveData()
        }

    }


    class StockViewModelFactory(private val repo: StockRepository) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StockViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return StockViewModel(repo) as T
            }

            throw IllegalArgumentException("Unknown viewModel class")
        }

    }


}