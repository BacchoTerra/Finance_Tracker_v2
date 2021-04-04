package com.bacchoterra.financetrackerv2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.adapter.StockHistoryAdapter
import com.bacchoterra.financetrackerv2.application.FinanceApplication
import com.bacchoterra.financetrackerv2.databinding.ActivityShowStockBinding
import com.bacchoterra.financetrackerv2.databinding.ViewStockButtonsBinding
import com.bacchoterra.financetrackerv2.fragments.StockOperationBtmSheet
import com.bacchoterra.financetrackerv2.model.Stock
import com.bacchoterra.financetrackerv2.utils.DateUtil
import com.bacchoterra.financetrackerv2.viewmodel.StockViewModel


class ShowStockActivity : AppCompatActivity(), StockOperationBtmSheet.OnStockUpdated {

    //Layout components
    private lateinit var binder: ActivityShowStockBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StockHistoryAdapter

    //Stock components
    private lateinit var stock: Stock

    //Deletion components
    private var firstClick = true

    //ViewModel
    private val viewModel: StockViewModel by viewModels {
        StockViewModel.StockViewModelFactory((application as FinanceApplication).stockRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityShowStockBinding.inflate(layoutInflater)
        setContentView(binder.root)
        retrieveStock()
        handleButtons()
    }


    private fun retrieveStock() {

        stock = intent.extras?.get(StocksActivity.KEY_RECYCLER_STOCK) as Stock

        bindStock(stock)
    }

    private fun bindStock(stock: Stock) {

        binder.activityShowStockTxtName.text = stock.name
        bindBroker(stock)

        binder.activityShowStockTxtNameQuantity.text = getString(R.string.quant_x, stock.quantity)
        binder.activityShowStockTxtFirstDate.text =
            DateUtil.formatDate(stock.initialTimestamp, DateUtil.DEFAULT_PATTERN)
        binder.activityShowStockTxtTechnique.text =
            stock.techniqueUsed ?: getString(R.string.not_defined)
        binder.activityShowStockTxtExpectedTime.text =
            stock.expectedTimeInvested ?: getString(R.string.not_defined)


        initStockHistory(stock)

    }

    private fun bindBroker(stock: Stock) {

        if (stock.broker == null) {

            binder.activityShowStockTxtBrokerAndType.text = null


        } else {
            binder.activityShowStockTxtBrokerAndType.text = stock.broker
        }


    }

    private fun initStockHistory(stock: Stock) {

        recyclerView = binder.activityShowStockRecyclerHistory
        adapter = StockHistoryAdapter(stock.history, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)


    }

    private fun handleButtons() {

        val bind = binder.activityShowStockViewButtons

        bind.fabDelete.setOnClickListener {

            handleStockDeletion(bind)

        }

        bind.fabAdd.setOnClickListener {

            openBottomSheet(StockOperationBtmSheet.ADD_OPERATION, stock)

        }

        bind.fabRemove.setOnClickListener {

            openBottomSheet(StockOperationBtmSheet.SELL_OPERATION, stock)
        }


    }

    private fun openBottomSheet(operation: Int, stock: Stock) {

        val operationBottomSheet = StockOperationBtmSheet()

        val bundle = Bundle()
        bundle.putInt(StockOperationBtmSheet.KEY_TYPE_ARGUMENTS, operation)
        bundle.putSerializable(StockOperationBtmSheet.KEY_STOCK_ARGUMENTS, stock)
        operationBottomSheet.arguments = bundle

        operationBottomSheet.show(supportFragmentManager, null)


    }

    private fun handleStockDeletion(btnBinder: ViewStockButtonsBinding) {

        when {

            firstClick -> {
                btnBinder.fabDelete.backgroundTintList =
                    AppCompatResources.getColorStateList(this, R.color.error_color)
                Toast.makeText(this, "Clique novamente para excluir", Toast.LENGTH_LONG).show()
                firstClick = false
            }

            else -> {
                viewModel.delete(stock)
                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
                finish()
            }

        }


    }

    override fun onBuyNewStock(updatedStock: Stock) {

        viewModel.update(updatedStock)
        bindStock(updatedStock)
        this.stock = updatedStock

    }

    override fun onSellNewStock(updatedStock: Stock) {
        viewModel.update(updatedStock)
        bindStock(updatedStock)
        this.stock = updatedStock
    }


}