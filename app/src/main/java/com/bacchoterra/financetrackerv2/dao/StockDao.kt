package com.bacchoterra.financetrackerv2.dao

import androidx.room.*
import com.bacchoterra.financetrackerv2.model.Stock
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {

    @Insert
    suspend fun insert(stock:Stock)

    @Update
    suspend fun update(stock:Stock)

    @Delete
    suspend fun delete(stock:Stock)

    @Query("DELETE FROM stock_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM stock_table")
    fun selectAll():Flow<List<Stock>>

}