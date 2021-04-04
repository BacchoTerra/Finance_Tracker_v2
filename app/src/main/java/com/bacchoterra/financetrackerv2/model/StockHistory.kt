package com.bacchoterra.financetrackerv2.model

import java.io.Serializable

class StockHistory (val timeStamp:Long,val quantity:Int,val isBuyOperation:Boolean,val price:Float) :Serializable{


}