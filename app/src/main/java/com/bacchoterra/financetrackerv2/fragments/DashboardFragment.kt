package com.bacchoterra.financetrackerv2.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.databinding.FragmentDashboardBinding
import com.bacchoterra.financetrackerv2.utils.SharedPrefsUtil
import com.bacchoterra.financetrackerv2.view.AddStockActivity
import com.bacchoterra.financetrackerv2.view.StocksActivity


class DashboardFragment : Fragment() {

    //Layout components
    private lateinit var binder: FragmentDashboardBinding
    private lateinit var recyclerView: RecyclerView

    //SharedPrefsManage
    private lateinit var sharedPrefsUtil: SharedPrefsUtil


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = FragmentDashboardBinding.inflate(inflater)

        inflateStockLastItem(inflater)

        return binder.root
    }

    private fun inflateStockLastItem(inflater: LayoutInflater){

        val containerLayout = binder.fragmentDashboardStockContainer
        val rowContainer:ViewGroup = containerLayout.viewDashboardLastItemRowContainer
        inflater.inflate(R.layout.row_stock,rowContainer,true)

        containerLayout.viewDashboardLastTxtSeeMore.setOnClickListener{

            requireActivity().startActivity(Intent(activity,StocksActivity::class.java))

        }


    }



}