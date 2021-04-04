package com.bacchoterra.financetrackerv2.utils

import com.bacchoterra.financetrackerv2.model.Stock
import com.bacchoterra.financetrackerv2.model.StockHistory

class StockHistoryManager(private val mStock: Stock) {

    //Stock fields to be changed
    private lateinit var mUpdatedStock: Stock
    private var mQuantity = mStock.quantity
    private var mAveragePrice = mStock.averagePrice
    private var mTotalSpent = mStock.totalSpent
    private var mHistory = arrayListOf<StockHistory>()


    fun addNewOperationToList(stockHistory: StockHistory, isBuyOperation: Boolean) {

        mStock.history.forEach { mHistory.add(it) }
        mHistory.add(stockHistory)

        updateNecessaryStockFieldsOnBuyOperation(stockHistory, isBuyOperation)

    }

    private fun updateNecessaryStockFieldsOnBuyOperation(
        stockHistory: StockHistory,
        isBuyOperation: Boolean
    ) {

        if (isBuyOperation) {
            mQuantity += stockHistory.quantity
            mTotalSpent += stockHistory.quantity * stockHistory.price
            mAveragePrice = mTotalSpent / mQuantity


        } else {
            mQuantity -= stockHistory.quantity
            mTotalSpent -= stockHistory.quantity * stockHistory.price
            mAveragePrice = mTotalSpent / mQuantity
        }

        mUpdatedStock = mStock.copy(
            quantity = mQuantity,
            totalSpent = mTotalSpent,
            averagePrice = mAveragePrice,
            history = mHistory
        )


    }

    fun requestUpdatedStock(): Stock {

        return this.mUpdatedStock

    }


}