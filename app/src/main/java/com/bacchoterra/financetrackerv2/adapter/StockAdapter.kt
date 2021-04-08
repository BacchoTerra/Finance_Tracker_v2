package com.bacchoterra.financetrackerv2.adapter

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.model.Stock
import com.bacchoterra.financetrackerv2.utils.DateUtil
import com.bacchoterra.financetrackerv2.utils.NumbersUtil
import com.bacchoterra.financetrackerv2.view.ShowStockActivity
import com.bacchoterra.financetrackerv2.view.StocksActivity

class StockAdapter(private val activity: Activity) :
    ListAdapter<Stock, StockAdapter.MyViewHolder>(StockComparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_stock, parent, false)

        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val stock = getItem(position)

        bindStock(holder, stock)


    }

    private fun bindStock(holder: MyViewHolder, stock: Stock) {

        holder.txtName.text = stock.name
        holder.txtDate.text = DateUtil.formatDate(stock.initialTimestamp,DateUtil.DEFAULT_PATTERN)
        holder.txtTotalSpent.text = activity.getString(
            R.string.money_symbol_price,
            NumbersUtil.roundAndFormatToCurrency(stock.totalSpent)
        )



        when {

            stock.isFinished -> {

                holder.imageStatus.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity,
                        R.drawable.ic_baseline_receipt_24
                    )
                )


            }

            else -> {

                holder.imageStatus.setImageDrawable(
                    ContextCompat.getDrawable(
                        activity,
                        R.drawable.ic_baseline_brightness_1_24
                    )
                )

                holder.imageStatus.imageTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        activity,
                        R.color.secondaryColor
                    )
                )


            }

        }

        holder.rootLayout.setOnClickListener {

            val intent = Intent(activity, ShowStockActivity::class.java)
            intent.putExtra(StocksActivity.KEY_RECYCLER_STOCK, stock)
            activity.startActivity(intent)

        }


    }


    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


        val rootLayout: ViewGroup = view.findViewById(R.id.row_stock_rootLayout)
        val imageStatus: ImageView = view.findViewById(R.id.row_stock_imageStatus)
        val txtName: TextView = view.findViewById(R.id.row_stock_txtName)
        val txtTotalSpent: TextView = view.findViewById(R.id.row_stock_txtTotalSpent)
        val txtDate: TextView = view.findViewById(R.id.row_stock_txtDate)


    }

    class StockComparator : DiffUtil.ItemCallback<Stock>() {

        override fun areItemsTheSame(oldItem: Stock, newItem: Stock): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Stock, newItem: Stock): Boolean {
            return oldItem.name == newItem.name && oldItem.initialTimestamp == newItem.initialTimestamp && oldItem.quantity == newItem.quantity && oldItem.averagePrice == newItem.averagePrice && oldItem.totalSpent == newItem.totalSpent && oldItem.history == newItem.history && oldItem.expectedTimeInvested == newItem.expectedTimeInvested && oldItem.isFinished == newItem.isFinished && oldItem.finalTimestamp == newItem.finalTimestamp && oldItem.techniqueUsed == newItem.techniqueUsed && oldItem.broker == newItem.broker && oldItem.finalPrice == newItem.finalPrice && oldItem.profit == newItem.profit && oldItem.id == newItem.id
        }
    }

}