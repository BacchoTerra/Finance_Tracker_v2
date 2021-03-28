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

class StockHistoryAdapter(private val list:List<StockHistory>,private val activity: Activity) :
    RecyclerView.Adapter<StockHistoryAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_stock_history, parent, false)

        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        bindStockHistory(holder,list[position])


    }

    private fun bindStockHistory(holder: MyViewHolder, stockHistory: StockHistory) {

        holder.txtDate.text = DateUtil.formatDate(stockHistory.timeStamp, DateUtil.DEFAULT_PATTERN)

        holder.txtType.text =
            if (stockHistory.isSoldOperation) {
                activity.getString(R.string.sold)
            } else {
                activity.getString(R.string.bought)
            }

        holder.txtQuantity.text = stockHistory.quantity.toString()


    }

    override fun getItemCount(): Int {
        return list.size
    }


    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtDate: TextView = view.findViewById(R.id.row_stock_history_txtDate)
        val txtType: TextView = view.findViewById(R.id.row_stock_history_txtType)

        val txtQuantity: TextView = view.findViewById(R.id.row_stock_history_txtQuantity)
    }
}
