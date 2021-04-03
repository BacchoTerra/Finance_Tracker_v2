package com.bacchoterra.financetrackerv2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "stock_table")
data class Stock(
    val name: String,
    val initialTimestamp: Long,
    val quantity: Int,
    val averagePrice: Float,
    val totalSpent: Float,
    val history: List<StockHistory>,
    val expectedTimeInvested: String? = null,
    val isFinished: Boolean = false,
    val finalTimestamp: String? = null,
    val techniqueUsed: String? = null,
    val broker: String? = null,
    val finalPrice: Float? = null,
    val profit: Float? = null,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Serializable {


}