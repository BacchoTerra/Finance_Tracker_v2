package com.bacchoterra.financetrackerv2.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.adapter.StockAdapter
import com.bacchoterra.financetrackerv2.application.FinanceApplication
import com.bacchoterra.financetrackerv2.databinding.ActivityStocksBinding
import com.bacchoterra.financetrackerv2.model.Stock
import com.bacchoterra.financetrackerv2.viewmodel.StockViewModel

class StocksActivity : AppCompatActivity() {

    //Layout components
    private lateinit var binder: ActivityStocksBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: StockAdapter

    //Stock components
    private lateinit var stock: Stock

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
        initToolbar()
        initRecyclerViewLayout()
        createActivityContract()
        fetchAllStocks()


    }

    private fun initToolbar() {

        setSupportActionBar(binder.activityStocksToolbar)

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

    private fun fetchAllStocks() {

        viewModel.allStock.observe(this, {

            recyclerViewAdapter.submitList(it)

        })

    }

    private fun insertNewStock(stock: Stock) {

        viewModel.insert(stock)
        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()

    }

    private fun initRecyclerViewLayout() {


        val recyclerViewLayoutBinder = binder.includedLayout

        recyclerView = recyclerViewLayoutBinder.stocksRecyclerView
        recyclerViewAdapter = StockAdapter(this)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        recyclerViewLayoutBinder.stocksTxtAdd.setOnClickListener {

            activityLauncher.launch(Intent(this, AddStockActivity::class.java))

        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_stock_toolbar, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.menu_stock_toolbar_filter_all -> Toast.makeText(this, "all", Toast.LENGTH_SHORT)
                .show()

            R.id.menu_stock_toolbar_filter_maior_menor -> Toast.makeText(
                this,
                "maior_menor",
                Toast.LENGTH_SHORT
            ).show()
            R.id.menu_stock_toolbar_filter_menor_maior -> Toast.makeText(
                this,
                "menor_manior",
                Toast.LENGTH_SHORT
            ).show()
            R.id.menu_stock_toolbar_filter_finalized -> Toast.makeText(
                this,
                "finalized",
                Toast.LENGTH_SHORT
            ).show()
            R.id.menu_stock_toolbar_filter_opened -> Toast.makeText(
                this,
                "opened",
                Toast.LENGTH_SHORT
            ).show()


        }

        return super.onOptionsItemSelected(item)
    }

}