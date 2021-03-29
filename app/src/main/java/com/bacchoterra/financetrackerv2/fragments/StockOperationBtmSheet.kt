package com.bacchoterra.financetrackerv2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.model.StockHistory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText

class StockOperationBtmSheet : BottomSheetDialogFragment() {

    companion object {

        const val KEY_ARGUMENTS = "ARGUMENTS"
        const val ADD_OPERATION = 0
        const val REMOVE_OPERATION = 1

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflateChosenLayout(inflater, container)
    }

    private fun inflateChosenLayout(inflater: LayoutInflater, container: ViewGroup?): View? {

        val view = inflater.inflate(R.layout.btm_sheet_add_stock_history,container,false)


        when (arguments?.get(KEY_ARGUMENTS)) {

            ADD_OPERATION -> handleAddOperation(view)

            REMOVE_OPERATION -> handleRemoveOperation(view)

            }

        return view
    }

    private fun handleAddOperation(view:View){
        view.findViewById<TextView>(R.id.btom_sheet_add_stock_history_txtHeader).text = getString(R.string.add_buy)

        view.findViewById<Button>(R.id.btom_sheet_add_stock_history_btnSave).setOnClickListener {

        }

    }

    private fun handleRemoveOperation(view:View){
        view.findViewById<TextView>(R.id.btom_sheet_add_stock_history_txtHeader).text = getString(R.string.add_sell)

        view.findViewById<Button>(R.id.btom_sheet_add_stock_history_btnSave).setOnClickListener {

        }

    }

    private fun buildStockHistory(view:View){

        val editPrice = view.findViewById<TextInputEditText>(R.id.btom_sheet_add_stock_history_editPrice)
        val editQuantity = view.findViewById<TextInputEditText>(R.id.btom_sheet_add_stock_history_editQuantity)

    }


}