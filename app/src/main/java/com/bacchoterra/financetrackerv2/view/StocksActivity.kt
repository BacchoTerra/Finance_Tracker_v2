package com.bacchoterra.financetrackerv2.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
import com.bacchoterra.financetrackerv2.repository.StockRepository
import com.bacchoterra.financetrackerv2.viewmodel.StockViewModel

class StocksActivity : AppCompatActivity() {

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_stock_toolbar, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.menu_stock_toolbar_filter_all -> fetchStocks(0)
            R.id.menu_stock_toolbar_filter_maior_menor -> fetchStocks(StockViewModel.ORDER_BY_DESC)
            R.id.menu_stock_toolbar_filter_menor_maior -> fetchStocks(StockViewModel.ORDER_BY_ASC)
            R.id.menu_stock_toolbar_filter_finalized -> fetchStocks(StockViewModel.FINISHED)
            R.id.menu_stock_toolbar_filter_opened -> fetchStocks(StockViewModel.UNFINISHED)

        }

        return true
    }

}