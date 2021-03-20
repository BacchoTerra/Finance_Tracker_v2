package com.bacchoterra.financetrackerv2.model

class StockHistory (timeStamp:Long,val operationType:Int,quantity:Int) {

    companion object {

        const val BUY = 0
        const val SELL = 1
    }

}