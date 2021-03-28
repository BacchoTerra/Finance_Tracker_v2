package com.bacchoterra.financetrackerv2.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.databinding.ActivityAddStockBinding
import com.bacchoterra.financetrackerv2.model.Stock
import com.bacchoterra.financetrackerv2.model.StockHistory
import com.blackcat.currencyedittext.CurrencyEditText
import com.google.android.material.textfield.TextInputEditText
import com.santalu.maskara.widget.MaskEditText
import java.text.SimpleDateFormat
import java.util.*

class AddStockActivity : AppCompatActivity() {

    //Layout components
    private lateinit var binder: ActivityAddStockBinding
    private lateinit var editName: TextInputEditText
    private lateinit var editQuantity: TextInputEditText
    private lateinit var checkBoxIsSold: CheckBox
    private lateinit var editDate: MaskEditText
    private lateinit var editPrice: TextInputEditText

    private lateinit var editBroker: TextInputEditText
    private lateinit var editPExpectedTime: TextInputEditText
    private lateinit var editTechinique: TextInputEditText


    //Stock components
    private lateinit var stock: Stock
    private var isSold = false
    private lateinit var name: String
    private lateinit var date: String
    private var price = 0f
    private var quantity = 0
    companion object{
      const val KEY_STOCK = "STOCK"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityAddStockBinding.inflate(layoutInflater)
        setContentView(binder.root)
        initViews()

        handleHiddenLayouts()

        binder.activityAddStockBtnSave.setOnClickListener {


            if (isAllFieldsOk()) {
                buildAndSendStockAsResult()
            } else {
                findWrongInputField()
            }

        }

    }

    private fun initViews() {

        checkBoxIsSold = binder.activityAddStockCheckBoxSellOp
        editName = binder.activityAddStockEditName
        editDate = binder.activityAddStockEditDate
        editPrice = binder.activityAddStockEditPrice
        editQuantity = binder.activityAddStockEditQuantity

        editBroker = binder.activityAddStockEditBroker
        editPExpectedTime = binder.activityAddStockEditExpectedTime
        editTechinique = binder.activityAddStockEditTechnique

        binder.activityAddStockTxtAddBasicInfo.requestFocus()

    }

    private fun isAllFieldsOk(): Boolean {

        name = editName.text.toString().trim()
        date = editDate.text.toString().trim()
        price =
            if (editPrice.text.toString().isNotBlank()) editPrice.text.toString().toFloat() else 0f
        quantity = if (editQuantity.text.toString().isNotBlank()) editQuantity.text.toString()
            .toInt() else 0


        return name.isNotBlank() && date.isNotBlank() && date.length == 10 && price > 0 && quantity > 0


    }

    private fun findWrongInputField() {

        when {
            name.isBlank() -> editName.error =
                getString(R.string.error_stock_name)
            date.isBlank() || date.length < 10 -> editDate.error =
                getString(R.string.error_invalid_date)
            price <= 0 -> editPrice.error =
                getString(R.string.error_invalid_stock_price)
            quantity <= 0 -> editQuantity.error =
                getString(R.string.error_invalid_stock_quantity)


        }


    }

    private fun buildAndSendStockAsResult() {

        val historyList = listOf(StockHistory(getStockDate()!!, checkBoxIsSold.isChecked, quantity))

        val broker = if (editBroker.text.toString().trim()
                .isNotBlank()
        ) editBroker.text.toString() else null

        val expectedTime = if (editPExpectedTime.text.toString().trim()
                .isNotBlank()
        ) editPExpectedTime.text.toString() else null

        val techniqueUsed = if (editTechinique.text.toString().trim()
                .isNotBlank()
        ) editTechinique.text.toString() else null

        val stock = Stock(
            name.toUpperCase(Locale.ROOT),
            getStockDate()!!,
            quantity,
            price,
            quantity * price,
            historyList,
            isSoldOperation = checkBoxIsSold.isChecked,
            expectedTimeInvested = expectedTime,
            techniqueUsed = techniqueUsed,
            broker = broker
        )

        val intent = Intent().apply {
            putExtra(KEY_STOCK, stock)
        }

        setResult(RESULT_OK, intent)
        finish()

    }

    private fun getStockDate(): Long? {

        //TODO: handle day,month, and year inputs manually to prevent user from using absurd dates
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val date = sdf.parse(date)

        return date?.time

    }

    private fun handleHiddenLayouts() {

        val txtAddBroker = binder.activityAddStockTxtAddBroker
        val txtAddExpectedTime = binder.activityAddStockTxtAddExpectedTime
        val txtAddTechnique = binder.activityAddStockTxtAddTechnique

        txtAddBroker.text = getString(R.string.add_broker, "+")
        txtAddExpectedTime.text = getString(R.string.add_expected_time, "+")
        txtAddTechnique.text = getString(R.string.add_technique, "+")

        val inputLayoutBroker = binder.activityAddStockInputLayoutAddBroker
        val inputLayoutExpectedTime = binder.activityAddStockInputLayoutAddExpectedTime
        val inputLayoutTechnique = binder.activityAddStockInputLayoutAddTechnique

        txtAddBroker.setOnClickListener {

            if (inputLayoutBroker.visibility == View.GONE) {
                inputLayoutBroker.visibility = View.VISIBLE
                txtAddBroker.text = getString(R.string.add_broker, "-")
            } else {
                inputLayoutBroker.visibility = View.GONE
                txtAddBroker.text = getString(R.string.add_broker, "+")
            }

        }

        txtAddExpectedTime.setOnClickListener {

            if (inputLayoutExpectedTime.visibility == View.GONE) {
                inputLayoutExpectedTime.visibility = View.VISIBLE
                txtAddExpectedTime.text = getString(R.string.add_expected_time, "-")
            } else {
                inputLayoutExpectedTime.visibility = View.GONE
                txtAddExpectedTime.text = getString(R.string.add_expected_time, "+")
            }

        }

        txtAddTechnique.setOnClickListener {

            if (inputLayoutTechnique.visibility == View.GONE) {
                inputLayoutTechnique.visibility = View.VISIBLE
                txtAddTechnique.text = getString(R.string.add_technique, "-")
            } else {
                inputLayoutTechnique.visibility = View.GONE
                txtAddTechnique.text = getString(R.string.add_technique, "+")

            }

        }


    }


}