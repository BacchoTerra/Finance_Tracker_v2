package com.bacchoterra.financetrackerv2.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.adapter.StockAdapter
import com.bacchoterra.financetrackerv2.application.FinanceApplication
import com.bacchoterra.financetrackerv2.databinding.ActivityStocksBinding
import com.bacchoterra.financetrackerv2.model.Stock
import com.bacchoterra.financetrackerv2.viewmodel.StockViewModel

class StocksActivity : AppCompatActivity(),PopupMenu.OnMenuItemClickListener{

    //Layout components
    private lateinit var binder: ActivityStocksBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: StockAdapter

    //Stock components
    private lateinit var stock: Stock
    private lateinit var allStock: LiveData<List<Stock>>

    companion object {
        const val KEY_RECYCLER_STOCK = "RECYCLER_STOCK"
    }

    //ViewModel
    private val viewModel: StockViewModel by viewModels {
        StockViewModel.StockViewModelFactory((application as FinanceApplication).stockRepository)
    }

    //Activity launcher
    private lateinit var activityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityStocksBinding.inflate(layoutInflater)
        setContentView(binder.root)
        initRecyclerViewLayout()
        createActivityContract()
        fetchStocks(0)

        binder.activityStocksTxtNew.setOnClickListener{

            activityLauncher.launch(Intent(this,AddStockActivity::class.java))

        }
        binder.activityStocksLayoutSearchView.layoutSearchEditAndFilterIconImageFilter.setOnClickListener{

            createFilterMenu()

        }


    }

    private fun createActivityContract() {

        activityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                if (result.resultCode == RESULT_OK) {
                    val data: Intent? = result.data
                    stock = data?.extras?.getSerializable(AddStockActivity.KEY_STOCK) as Stock
                    insertNewStock(stock)
                }


            }

    }

    private fun fetchStocks(filter: Int) {

        allStock = viewModel.selectAllStockWithFilter(filter)

        allStock.observe(this) {

            recyclerViewAdapter.submitList(it)

        }


    }

    private fun insertNewStock(stock: Stock) {

        viewModel.insert(stock)
        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()

    }

    private fun initRecyclerViewLayout() {



        recyclerView = binder.activityStocksRecyclerView
        recyclerViewAdapter = StockAdapter(this)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)


    }

    private fun createFilterMenu() {

        val popupMenu = PopupMenu(this,binder.activityStocksLayoutSearchView.layoutSearchEditAndFilterIconImageFilter)
        popupMenu.menuInflater.inflate(R.menu.menu_stock_filtering,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(this)
        popupMenu.show()



    }

    override fun onMenuItemClick(p0: MenuItem?): Boolean {

        when (p0?.itemId) {

            R.id.menu_stock_toolbar_filter_all -> fetchStocks(0)
            R.id.menu_stock_toolbar_filter_maior_menor -> fetchStocks(StockViewModel.ORDER_BY_TOTAL_SPENT_DESC)
            R.id.menu_stock_toolbar_filter_menor_maior -> fetchStocks(StockViewModel.ORDER_BY_TOTAL_SPENT_ASC)
            R.id.menu_stock_toolbar_filter_finalized -> fetchStocks(StockViewModel.FINISHED)
            R.id.menu_stock_toolbar_filter_opened -> fetchStocks(StockViewModel.UNFINISHED)

        }

        return true
    }

}