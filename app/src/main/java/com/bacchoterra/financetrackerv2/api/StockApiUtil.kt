package com.bacchoterra.financetrackerv2.api

import android.app.Activity
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.GlobalScope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StockApiUtil (val activity: Activity){

    companion object {

        private const val URL = "https://alex-stock-market-api.herokuapp.com"


        fun getApi(): StockApi {

            return Retrofit.Builder().baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(StockApi::class.java)
        }

    }

}