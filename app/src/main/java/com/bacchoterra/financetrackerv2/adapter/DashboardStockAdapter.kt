package com.bacchoterra.financetrackerv2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.model.Stock
import java.util.*

class DashboardStockAdapter(val context: Context) :
    ListAdapter<Stock, DashboardStockAdapter.MyStockViewHolder>(StockAdapter.StockComparator()) {

    //Date checker
    private var calendar: Calendar = Calendar.getInstance()
    private var currentMonthAndYear = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyStockViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_dashboard_stocks, parent, false)

        return MyStockViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyStockViewHolder, position: Int) {

        val stock = getItem(position)

        bindStock(holder, stock)

    }


    class MyStockViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtDate: TextView = itemView.findViewById(R.id.row_dashboard_stock_txtDate)
        val imageStatus: ImageView =
            itemView.findViewById(R.id.row_dashboard_stock_imageStatus)
        val txtName: TextView = itemView.findViewById(R.id.row_dashboard_stock_txtName)
        val txtValue: TextView = itemView.findViewById(R.id.row_dashboard_stock_txtValue)


    }


    private fun bindStock(holder: MyStockViewHolder, stock: Stock) {

        holder.txtName.text = stock.name
        holder.txtValue.text = stock.totalSpent.toString()

        when {
            stock.isFinished -> holder.imageStatus.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_baseline_brightness_1_24
                )
            )
            else -> holder.imageStatus.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_baseline_receipt_24
                )
            )
        }

        handleDateDisplaying(holder,stock)

    }

    private fun handleDateDisplaying(holder: MyStockViewHolder,stock: Stock) {

        if (currentMonthAndYear == 0 || isDifferentDate(stock)) {
            calendar.timeInMillis = stock.initialTimestamp
            currentMonthAndYear = calendar.get(Calendar.MONTH) + calendar.get(Calendar.YEAR)
            displayStockDate(holder,stock)
            return
        }

        holder.txtDate.visibility = View.GONE


    }

    private fun displayStockDate(holder: MyStockViewHolder,stock: Stock) {

        calendar.timeInMillis = stock.initialTimestamp

        val display = context.getString(R.string.label_month_display_name_and_year,calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT,
            Locale.getDefault()),calendar.get(Calendar.YEAR))

        holder.txtDate.visibility = View.VISIBLE
        holder.txtDate.text = display


    }

    private fun isDifferentDate(stock: Stock):Boolean {

        calendar.timeInMillis = stock.initialTimestamp

        val newMonthAndYear = calendar.get(Calendar.MONTH) + calendar.get(Calendar.YEAR)

        return currentMonthAndYear != newMonthAndYear


    }


}