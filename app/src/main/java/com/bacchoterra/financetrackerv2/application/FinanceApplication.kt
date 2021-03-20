package com.bacchoterra.financetrackerv2.application

import android.app.Application
import com.bacchoterra.financetrackerv2.database.FinanceDatabase
import com.bacchoterra.financetrackerv2.repository.StockRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FinanceApplication :Application(){

    val database by lazy {  FinanceDatabase.getInstance(this)}

    val stockRepository by lazy { StockRepository(database.stockDao()) }

}