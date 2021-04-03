package com.bacchoterra.financetrackerv2.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.databinding.BtmSheetAddStockHistoryBinding
import com.bacchoterra.financetrackerv2.model.Stock
import com.bacchoterra.financetrackerv2.model.StockHistory
import com.bacchoterra.financetrackerv2.utils.StockHistoryManager
import com.blackcat.currencyedittext.CurrencyEditText
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import java.lang.ClassCastException

class StockOperationBtmSheet : BottomSheetDialogFragment() {

    companion object {

        const val KEY_TYPE_ARGUMENTS = "TYPE_ARGUMENTS"
        const val KEY_STOCK_ARGUMENTS = "STOCK_ARGUMENTS"
        const val ADD_OPERATION = 0
        const val SELL_OPERATION = 1

    }

    //Layout components
    private lateinit var binder: BtmSheetAddStockHistoryBinding
    private lateinit var editPrice: CurrencyEditText
    private lateinit var editQuantity: TextInputEditText
    private lateinit var btnSave: Button

    //Stock components
    private lateinit var stock: Stock

    //Listener
    private lateinit var mListener: OnStockUpdated


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        retrieveStock()
        initViews(inflater)
        handleLayoutDisplayName()
        checkFieldAndDoOperation()


        return binder.root
    }

    override fun onAttach(context: Context) {

        try {
            mListener = context as OnStockUpdated
        } catch (e: ClassCastException) {
            throw ClassCastException("Please, implement ${mListener::class}")
        }

        super.onAttach(context)
    }

    private fun initViews(inflater: LayoutInflater) {

        binder = BtmSheetAddStockHistoryBinding.inflate(inflater)
        editQuantity = binder.btomSheetAddStockHistoryEditQuantity
        editPrice = binder.btomSheetAddStockHistoryEditPrice
        btnSave = binder.btomSheetAddStockHistoryBtnSave


    }

    private fun retrieveStock() {
        stock = arguments?.get(KEY_STOCK_ARGUMENTS) as Stock
    }

    private fun handleLayoutDisplayName() {
        when (arguments?.get(KEY_TYPE_ARGUMENTS)) {

            ADD_OPERATION -> binder.btomSheetAddStockHistoryTxtHeader.text =
                getString(R.string.add_buy)
            SELL_OPERATION -> binder.btomSheetAddStockHistoryTxtHeader.text =
                getString(R.string.add_sell)


        }
    }

    private fun addBuyOperation() {
        val stockHistoryManager = StockHistoryManager(stock)

        stockHistoryManager.addNewOperationToList(createNewStockHistory())
        mListener.onBuyNewStock(stockHistoryManager.requestUpdatedStock())
        dismiss()

    }

    private fun createNewStockHistory(): StockHistory {

        val quantity = binder.btomSheetAddStockHistoryEditQuantity.text.toString().toInt()
        val price = (binder.btomSheetAddStockHistoryEditPrice.rawValue / 100.0).toFloat()

        return StockHistory(System.currentTimeMillis(), quantity, price)
    }

    private fun checkFieldAndDoOperation() {

        btnSave.setOnClickListener {


            val price = editPrice.rawValue / 100.0
            val quantity = editQuantity.text.toString()

            if (price > 0) {
                if (quantity.isNotBlank() && quantity.toInt() > 0) {

                    when (arguments?.getInt(KEY_TYPE_ARGUMENTS)) {

                        ADD_OPERATION -> addBuyOperation()
                        SELL_OPERATION -> Log.i("Porsche", "onCreateView: Not yet")

                    }

                } else {
                    editQuantity.error = "*"
                }
            } else {
                editPrice.error = "*"
                Log.i("porsche", "checkFieldAndDoOperation: $price")
            }

        }
    }

    interface OnStockUpdated {

        fun onBuyNewStock(updatedStock: Stock)

        fun onSellNewStock()

    }


}