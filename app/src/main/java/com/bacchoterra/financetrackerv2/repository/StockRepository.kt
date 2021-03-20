package com.bacchoterra.financetrackerv2.repository

import com.bacchoterra.financetrackerv2.dao.StockDao
import com.bacchoterra.financetrackerv2.model.Stock

class StockRepository (val stockDao:StockDao){


    val allStock = stockDao.selectAll()

    suspend fun insert(stock:Stock) {

        stockDao.insert(stock)

    }

    suspend fun update(stock:Stock) {

        stockDao.update(stock)

    }

    suspend fun delete(stock:Stock) {

        stockDao.delete(stock)

    }

}