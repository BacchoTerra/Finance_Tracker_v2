package com.bacchoterra.financetrackerv2.model

data class InvestmentTypes (val type:String,val imageRes:Int){


    companion object {

        const val STOCK_TYPE = "stock_type"
        const val FIXED_TYPE = "fixed_type"
        const val FUNDS_TYPE = "funds_type"


    }

}