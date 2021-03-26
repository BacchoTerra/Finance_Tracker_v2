package com.bacchoterra.financetrackerv2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.databinding.ActivityAddStockBinding
import com.google.android.material.textfield.TextInputEditText

class AddStockActivity : AppCompatActivity() {

    //Layout components
    private lateinit var binder: ActivityAddStockBinding
    private lateinit var editName: TextInputEditText
    private lateinit var editQuantity: TextInputEditText
    private lateinit var editType: AutoCompleteTextView
    private lateinit var editDate: TextInputEditText
    private lateinit var editPrice: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityAddStockBinding.inflate(layoutInflater)
        setContentView(binder.root)
        initTypeDropdownMenu()
        binder.activityAddStockTxtAddBasicInfo.requestFocus()

        handleHiddenLayouts()

    }

    private fun initTypeDropdownMenu(){

        val items = resources.getStringArray(R.array.stock_operation_types)
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,items)

        binder.activityAddStockEditType.setAdapter(adapter)

    }

    private fun handleHiddenLayouts(){

        val txtAddBroker = binder.activityAddStockTxtAddBroker
        val txtAddExpectedTime = binder.activityAddStockTxtAddExpectedTime
        val txtAddTechnique = binder.activityAddStockTxtAddTechnique

        txtAddBroker.text = getString(R.string.add_broker,"+")
        txtAddExpectedTime.text = getString(R.string.add_expected_time,"+")
        txtAddTechnique.text = getString(R.string.add_technique,"+")

        val inputLayoutBroker = binder.activityAddStockInputLayoutAddBroker
        val inputLayoutExpectedTime = binder.activityAddStockInputLayoutAddExpectedTime
        val inputLayoutTechnique = binder.activityAddStockInputLayoutAddTechnique

        txtAddBroker.setOnClickListener{

            if (inputLayoutBroker.visibility == View.GONE){
                inputLayoutBroker.visibility = View.VISIBLE
                txtAddBroker.text = getString(R.string.add_broker, "-")
            }else{
                inputLayoutBroker.visibility = View.GONE
                txtAddBroker.text = getString(R.string.add_broker, "+")
            }

        }

        txtAddExpectedTime.setOnClickListener{

            if (inputLayoutExpectedTime.visibility == View.GONE){
                inputLayoutExpectedTime.visibility = View.VISIBLE
                txtAddExpectedTime.text = getString(R.string.add_expected_time, "-")
            }else{
                inputLayoutExpectedTime.visibility = View.GONE
                txtAddExpectedTime.text = getString(R.string.add_expected_time, "+")
            }

        }

        txtAddTechnique.setOnClickListener{

            if (inputLayoutTechnique.visibility == View.GONE){
                inputLayoutTechnique.visibility = View.VISIBLE
                txtAddTechnique.text = getString(R.string.add_technique, "-")
            }else{
                inputLayoutTechnique.visibility = View.GONE
                txtAddTechnique.text = getString(R.string.add_technique, "+")

            }

        }




    }


}