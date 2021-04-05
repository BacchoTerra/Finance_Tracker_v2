package com.bacchoterra.financetrackerv2.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StockApi {

    @GET("/api/v1/stock/value/{stockName}")
    suspend fun getStockInfo(@Path("stockName") stockName: String): Response<ApiStockModel>

}