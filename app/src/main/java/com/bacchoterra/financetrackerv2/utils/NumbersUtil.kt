package com.bacchoterra.financetrackerv2.utils

import java.lang.StringBuilder
import java.math.BigDecimal
import java.text.NumberFormat


class NumbersUtil {

    companion object {


        private fun round(value: Float): Double {
            var bd = BigDecimal(value.toString())
            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP)
            return bd.toDouble()
        }


        fun roundAndFormatToCurrency(value:Float) : String{
            val numberFormat = NumberFormat.getCurrencyInstance()
            val formattedString =  numberFormat.format(round(value))

            val builder = StringBuilder(formattedString)

            return builder.insert(2," ").toString()

        }

    }

}