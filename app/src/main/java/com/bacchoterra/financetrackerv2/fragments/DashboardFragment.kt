package com.bacchoterra.financetrackerv2.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.adapter.InvestmentTypesAdapter
import com.bacchoterra.financetrackerv2.databinding.FragmentDashboardBinding
import com.bacchoterra.financetrackerv2.utils.SharedPrefsUtil


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

        initObjects(requireActivity())
        bindInitialInformation()
        populateRecyclerView()

        return binder.root
    }

    private fun initObjects(activity: Activity) {

        sharedPrefsUtil = SharedPrefsUtil(activity)

    }

    private fun bindInitialInformation() {

        binder.fragmentDashboardTxtWelcome.text = getString(R.string.hello_message,sharedPrefsUtil.getUserName())

    }

    private fun populateRecyclerView() {

        recyclerView = binder.fragmentDashboardRecyclerView
        val adapter = InvestmentTypesAdapter(requireActivity())
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)


    }

}