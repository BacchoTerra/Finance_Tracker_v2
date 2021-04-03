package com.bacchoterra.financetrackerv2.repository

import com.bacchoterra.financetrackerv2.dao.StockDao
import com.bacchoterra.financetrackerv2.model.Stock
import kotlinx.coroutines.flow.Flow

class StockRepository(private val stockDao: StockDao) {


    suspend fun insert(stock: Stock) {

        stockDao.insert(stock)

    }

    suspend fun update(stock: Stock) {

        stockDao.update(stock)

    }

    suspend fun delete(stock: Stock) {

        stockDao.delete(stock)

    }

    suspend fun deleteAll() {

        stockDao.deleteAll()

    }

    fun selectAll(): Flow<List<Stock>> {

        return stockDao.selectAll()

    }

    fun selectAllOrderByDesc(): Flow<List<Stock>> {

        return stockDao.selectAllOrderByDesc()

    }

    fun selectAllOrderByAsc(): Flow<List<Stock>> {

        return stockDao.selectAllOrderByAsc()

    }

    fun selectAllFinished(): Flow<List<Stock>> {

        return stockDao.selectAllFinished()

    }

    fun selectAllUnfinished(): Flow<List<Stock>> {

        return stockDao.selectAllUnfinished()

    }


}