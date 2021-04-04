package com.bacchoterra.financetrackerv2.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.model.StockHistory
import com.bacchoterra.financetrackerv2.utils.DateUtil
import com.bacchoterra.financetrackerv2.utils.NumbersUtil

class StockHistoryAdapter(private val list: List<StockHistory>, private val activity: Activity) :
    RecyclerView.Adapter<StockHistoryAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_stock_history, parent, false)

        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        bindStockHistory(holder, list[position])


    }

    private fun bindStockHistory(holder: MyViewHolder, stockHistory: StockHistory) {

        val type = if (stockHistory.isBuyOperation) activity.getString(R.string.bought) else activity.getString(R.string.sold)

        holder.txtTypeDate.text = activity.getString(
            R.string.stock_history_type_date,
            type,
            DateUtil.formatDate(stockHistory.timeStamp, DateUtil.DEFAULT_PATTERN)
        )

        holder.txtQuantityAndPrice.text = activity.getString(R.string.stock_history_quant_price,stockHistory.quantity,stockHistory.price)

        holder.txtTotalPrice.text = activity.getString(R.string.money_symbol_price,NumbersUtil.roundAndFormatToCurrency((stockHistory.quantity * stockHistory.price)))


    }

    override fun getItemCount(): Int {
        return list.size
    }


    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtTypeDate: TextView = view.findViewById(R.id.row_stock_history_txtTypeAndDate)
        val txtQuantityAndPrice: TextView =
            view.findViewById(R.id.row_stock_history_txtQuantityAndPrice)
        val txtTotalPrice: TextView = view.findViewById(R.id.row_stock_history_txtTotalPrice)
    }
}
