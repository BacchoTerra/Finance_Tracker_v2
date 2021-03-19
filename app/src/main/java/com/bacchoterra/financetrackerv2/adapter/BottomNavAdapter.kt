package com.bacchoterra.financetrackerv2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class BottomNavAdapter(fragActivity:FragmentActivity, private val list:List<Fragment>) : FragmentStateAdapter(fragActivity){


    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }

}