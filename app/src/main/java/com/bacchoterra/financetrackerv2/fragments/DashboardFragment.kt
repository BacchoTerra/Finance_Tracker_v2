package com.bacchoterra.financetrackerv2.fragments

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.adapter.DashboardStockAdapter
import com.bacchoterra.financetrackerv2.application.FinanceApplication
import com.bacchoterra.financetrackerv2.databinding.FragmentDashboardBinding
import com.bacchoterra.financetrackerv2.utils.SharedPrefsUtil
import com.bacchoterra.financetrackerv2.view.StocksActivity
import com.bacchoterra.financetrackerv2.viewmodel.StockViewModel
import java.util.*


class DashboardFragment : Fragment() {

    //Layout components
    private lateinit var binder: FragmentDashboardBinding

    //RecyclerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DashboardStockAdapter


    //SharedPrefsManage
    private lateinit var sharedPrefsUtil: SharedPrefsUtil

    //ViewModel
    private val stockViewModel:StockViewModel by viewModels {
        StockViewModel.StockViewModelFactory((requireActivity().application as FinanceApplication).stockRepository)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binder = FragmentDashboardBinding.inflate(inflater)

        greetUser()
        initRecyclerView()

        stockViewModel.selectAllStockWithFilter(StockViewModel.ORDER_BY_DATE_DESC).observe(viewLifecycleOwner, {

            adapter.submitList(it)

        })



        binder.fragmentDashboardBtnSeeMoreStocks.setOnClickListener{

            startActivity(Intent(activity,StocksActivity::class.java))

        }

        return binder.root
    }

    private fun greetUser(){

        sharedPrefsUtil = SharedPrefsUtil(requireActivity())
        binder.fragmentDashboardTxtName.text = sharedPrefsUtil.getUserName()

        val calendar = Calendar.getInstance()

        when (calendar.get(Calendar.HOUR_OF_DAY)) {

            in (4..11) -> {
                setDateTimeGreetings(getString(R.string.good_morning))

            }
            in (12..18) -> {
                setDateTimeGreetings(getString(R.string.good_afternoon))
            }
            else -> {
                setDateTimeGreetings(getString(R.string.good_evening))
            }

        }



        binder.fragmentDashboardTxtDate.text = getString(R.string.label_day_month_year,calendar.get(Calendar.DAY_OF_MONTH),calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.getDefault()),calendar.get(Calendar.YEAR))


    }

    private fun setDateTimeGreetings(greet:String){

        binder.fragmentDashboardTxtGreetings.text = greet
    }

    private fun initRecyclerView(){

        recyclerView = binder.fragmentDashboardRecyclerView

        adapter = DashboardStockAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

    }


}