package com.bacchoterra.financetrackerv2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock_table")
data class Stock(
    val name: String,
    val initialTimestamp: Long,
    val quantity: Int,
    val averagePrice: Float,
    val totalSpent: Float,
    val type:Int,
    val history:List<StockHistory>,
    val expectedTimeInvested: String? = null,
    val finished: Boolean = false,
    val finalTimestamp: String? = null,
    val techniqueUsed: String? = null,
    val broker: String? = null,
    val finalPrice: Float? = null,
    val profit: Float? = null,
    @PrimaryKey(autoGenerate = true) val id: Int =0
) {

    companion object {
        private const val TYPE_BUY = 0
        private const val TYPE_SELL = 1
    }


}