package com.bacchoterra.financetrackerv2.view

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.adapter.StockHistoryAdapter
import com.bacchoterra.financetrackerv2.application.FinanceApplication
import com.bacchoterra.financetrackerv2.databinding.ActivityShowStockBinding
import com.bacchoterra.financetrackerv2.databinding.FragmentDashboardBinding
import com.bacchoterra.financetrackerv2.databinding.ViewStockButtonsBinding
import com.bacchoterra.financetrackerv2.fragments.StockOperationBtmSheet
import com.bacchoterra.financetrackerv2.model.Stock
import com.bacchoterra.financetrackerv2.model.StockHistory
import com.bacchoterra.financetrackerv2.utils.DateUtil
import com.bacchoterra.financetrackerv2.viewmodel.StockViewModel
import com.google.android.material.snackbar.Snackbar


class ShowStockActivity : AppCompatActivity() {

    //Layout components
    private lateinit var binder: ActivityShowStockBinding
    private lateinit var recyclerView: RecyclerView

    //Stock components
    private lateinit var stock: Stock

    //Deletion components
    private var firstClick = true

    //ViewModel
    private val viewModel:StockViewModel by viewModels {
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

        bindStock()
    }

    private fun bindStock() {

        binder.activityShowStockTxtName.text = stock.name
        bindBrokerAndType()

        binder.activityShowStockTxtNameQuantity.text = getString(R.string.quant_x, stock.quantity)
        binder.activityShowStockTxtFirstDate.text =
            DateUtil.formatDate(stock.initialTimestamp, DateUtil.DEFAULT_PATTERN)
        binder.activityShowStockTxtTechnique.text =
            stock.techniqueUsed ?: getString(R.string.not_defined)
        binder.activityShowStockTxtExpectedTime.text =
            stock.expectedTimeInvested ?: getString(R.string.not_defined)


        initStockHistory()

    }

    private fun bindBrokerAndType() {

        val operationType =
            if (stock.isSoldOperation) getString(R.string.opera_o_vendida) else getString(R.string.oper_o_comprada)

        if (stock.broker == null) {

            binder.activityShowStockTxtBrokerAndType.text =
                getString(R.string.type_only, operationType)

        } else {
            binder.activityShowStockTxtBrokerAndType.text =
                getString(R.string.broker_and_type, stock.broker, operationType)
        }


    }

    private fun initStockHistory() {

        recyclerView = binder.activityShowStockRecyclerHistory
        val adapter = StockHistoryAdapter(stock.history, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)


    }

    private fun handleButtons() {

        val bind = binder.activityShowStockViewButtons

        bind.fabDelete.setOnClickListener {

            handleStockDeletion(bind)

        }


    }

    private fun openBottomSheet(operation: Int) {

        val operationBottomSheet = StockOperationBtmSheet()

        val bundle = Bundle()
        bundle.putInt(StockOperationBtmSheet.KEY_ARGUMENTS, operation)
        operationBottomSheet.arguments = bundle

        operationBottomSheet.show(supportFragmentManager, null)


    }

    private fun handleStockDeletion(btnBinder: ViewStockButtonsBinding) {

        when {

            firstClick -> {
                btnBinder.fabDelete.backgroundTintList = AppCompatResources.getColorStateList(this,R.color.error_color)
                Toast.makeText(this,"Clique novamente para excluir",Toast.LENGTH_LONG).show()
                firstClick = false
            }

            else -> {
                viewModel.delete(stock)
                Toast.makeText(this,"done",Toast.LENGTH_SHORT).show()
                finish ()
            }

        }


    }


}