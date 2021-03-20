package com.bacchoterra.financetrackerv2.converter

import androidx.room.TypeConverter
import com.bacchoterra.financetrackerv2.model.Stock
import com.bacchoterra.financetrackerv2.model.StockHistory
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

class Converters {
    @TypeConverter
    fun listToJson(value: List<StockHistory>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<StockHistory>::class.java).toList()
}
