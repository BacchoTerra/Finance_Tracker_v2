package com.bacchoterra.financetrackerv2.view


import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.adapter.BottomNavAdapter
import com.bacchoterra.financetrackerv2.application.FinanceApplication
import com.bacchoterra.financetrackerv2.databinding.ActivityMainBinding
import com.bacchoterra.financetrackerv2.fragments.DashboardFragment
import com.bacchoterra.financetrackerv2.fragments.InformationFragment
import com.bacchoterra.financetrackerv2.fragments.ProfileFragment
import com.bacchoterra.financetrackerv2.model.Stock
import com.bacchoterra.financetrackerv2.model.StockHistory
import com.bacchoterra.financetrackerv2.viewmodel.StockViewModel

class MainActivity : FragmentActivity() {

    //Layout components
    private lateinit var binder: ActivityMainBinding

    //Viewpager objects
    private lateinit var viewPager2: ViewPager2
    private lateinit var list: ArrayList<Fragment>

    //ViewModel
    private val viewModel: StockViewModel by viewModels {
        StockViewModel.StockViewModelFactory((application as FinanceApplication).stockRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binder.root)

        initViewPager()
        handleBtmNav()
    }

    private fun handleBtmNav() {

        val btmNav = binder.activityMainBtmNav

        btmNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.btm_nav_main_menu_dashboard -> {
                    viewPager2.setCurrentItem(0, false)
                    true
                }
                R.id.btm_nav_main_menu_info -> {
                    viewPager2.setCurrentItem(1, false)
                    true
                }

                R.id.btm_nav_main_menu_profile -> {
                    viewPager2.setCurrentItem(2, false)
                    true
                }
                else -> false
            }

        }


    }

    private fun initViewPager() {

        viewPager2 = binder.activityMainViewPager

        list = ArrayList()
        list.add(DashboardFragment())
        list.add(InformationFragment())
        list.add(ProfileFragment())

        val fragStateAdapter = BottomNavAdapter(this, list)
        viewPager2.adapter = fragStateAdapter

        viewPager2.isUserInputEnabled = false

    }


}