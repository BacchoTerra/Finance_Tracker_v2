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

    //Booleans dont exists..its turned to integer values, 0 being true and 1 being false
    @Query("SELECT * FROM stock_table WHERE isFinished = 0")
    fun selectAllUnfinished(): Flow<List<Stock>>

    @Query("SELECT * FROM stock_table WHERE isFinished = 1")
    fun selectAllFinished():Flow<List<Stock>>

    @Query("SELECT * FROM stock_table ORDER BY totalSpent DESC")
    fun selectAllOrderByTotalSpentDesc():Flow<List<Stock>>

    @Query("SELECT * FROM stock_table ORDER BY totalSpent ASC")
    fun selectAllOrderByTotalSpentAsc():Flow<List<Stock>>

    @Query("SELECT * FROM stock_table ORDER BY initialTimestamp DESC")
    fun selectAllOrderByDateDes():Flow<List<Stock>>


}