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
import androidx.recyclerview.widget.RecyclerView
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.databinding.FragmentDashboardBinding
import com.bacchoterra.financetrackerv2.utils.SharedPrefsUtil
import com.bacchoterra.financetrackerv2.view.StocksActivity
import java.util.*


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
        greetUser()
        handleIncludedLayoutViews()

        return binder.root
    }

    private fun greetUser(){

        sharedPrefsUtil = SharedPrefsUtil(requireActivity())
        val calendar = Calendar.getInstance()

        when (calendar.get(Calendar.HOUR_OF_DAY)) {

            in (4..11) -> {
                setGreetTextAndDrawable(getString(R.string.good_morning),R.drawable.ic_baseline_light_mode_24,Color.YELLOW)

            }
            in (12..18) -> {
                setGreetTextAndDrawable(getString(R.string.good_afternoon),R.drawable.ic_baseline_light_mode_24,Color.YELLOW)
            }
            else -> {
                setGreetTextAndDrawable(getString(R.string.good_evening),R.drawable.ic_baseline_nights_stay_24,Color.LTGRAY)
            }

        }



        binder.fragmentDashboardTxtDate.text = getString(R.string.label_day_month_year,calendar.get(Calendar.DAY_OF_MONTH),calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.getDefault()),calendar.get(Calendar.YEAR))


    }

    private fun setGreetTextAndDrawable(greet:String,drawable:Int,color:Int){

        binder.fragmentDashboardTxtHello.text = getString(R.string.label_greet_user,greet,sharedPrefsUtil.getUserName())
        binder.fragmentDashboardTxtHello.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable,0,0,0)
        TextViewCompat.setCompoundDrawableTintList(binder.fragmentDashboardTxtHello, ColorStateList.valueOf(color))
    }

    private fun handleIncludedLayoutViews(){

        val includedBinder = binder.fragmentDashboardViewRecyclerAndButton
        includedBinder.stocksTxtAdd.text = getString(R.string.ver_todas)

        includedBinder.stocksTxtAdd.setOnClickListener{

            requireActivity().startActivity(Intent(requireActivity(),StocksActivity::class.java))

        }

    }


}