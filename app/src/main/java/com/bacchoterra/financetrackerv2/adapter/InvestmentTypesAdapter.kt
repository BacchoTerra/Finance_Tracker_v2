package com.bacchoterra.financetrackerv2.adapter

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.model.InvestmentTypes
import com.bacchoterra.financetrackerv2.view.StocksActivity

class InvestmentTypesAdapter(private val activity: Activity) :
    RecyclerView.Adapter<InvestmentTypesAdapter.MyViewHolder>() {

    private lateinit var listOfItems: ArrayList<InvestmentTypes>

    init {
        populateViewHolder()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_investment_types, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val investmentTypes = listOfItems[position]

        bindLayout(holder, investmentTypes)

    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val rootLayout: FrameLayout = view.findViewById(R.id.row_investment_type_rootLayout)
        val imageView: ImageView = view.findViewById(R.id.row_investment_type_imageView)
        val txtType: TextView = view.findViewById(R.id.row_investment_type_txtType)

    }

    private fun bindLayout(holder: MyViewHolder, investmentTypes: InvestmentTypes) {

        holder.imageView.setImageResource(investmentTypes.imageRes)
        holder.imageView.imageTintList =
            ColorStateList.valueOf(activity.resources.getColor(R.color.textColor))

        when (investmentTypes.type) {

            InvestmentTypes.STOCK_TYPE -> holder.txtType.text =
                activity.getString(R.string.stock_type)
            InvestmentTypes.FIXED_TYPE -> holder.txtType.text =
                activity.getString(R.string.fixed_type)
            InvestmentTypes.FUNDS_TYPE -> holder.txtType.text =
                activity.getString(R.string.fund_type)


        }

        holder.rootLayout.setOnClickListener {

            when (investmentTypes.type) {

                InvestmentTypes.STOCK_TYPE -> {
                    activity.startActivity(Intent(activity.baseContext, StocksActivity::class.java))
                }


            }

        }


    }

    private fun populateViewHolder() {

        listOfItems = arrayListOf(
            InvestmentTypes(InvestmentTypes.STOCK_TYPE, R.drawable.ic_round_trending_up_24),
            InvestmentTypes(InvestmentTypes.FIXED_TYPE, R.drawable.ic_round_work_outline_24),
            InvestmentTypes
                (InvestmentTypes.FUNDS_TYPE, R.drawable.ic_baseline_group_work_24)
        )


    }
}