package com.bacchoterra.financetrackerv2.model

import java.io.Serializable

class StockHistory (val timeStamp:Long,val isSoldOperation:Boolean,val quantity:Int,val price:Float) :Serializable{


}