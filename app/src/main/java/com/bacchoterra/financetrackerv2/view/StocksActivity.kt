package com.bacchoterra.financetrackerv2.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.databinding.ActivityStocksBinding

class StocksActivity : AppCompatActivity() {

    //Layout components
    private lateinit var binder:ActivityStocksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityStocksBinding.inflate(layoutInflater)
        setContentView(binder.root)
        initToolbar()


    }

    private fun initToolbar(){

        setSupportActionBar(binder.activityStocksToolbar)
        binder.activityStocksRecyclerView.requestFocus()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

       menuInflater.inflate(R.menu.menu_toolbar_add,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_toolbar_add_add){
            startActivity(Intent(this,AddStockActivity::class.java))
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}