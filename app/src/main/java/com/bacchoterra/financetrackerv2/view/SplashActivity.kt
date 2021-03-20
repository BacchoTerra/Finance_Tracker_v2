package com.bacchoterra.financetrackerv2.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.databinding.ActivitySplashBinding
import com.bacchoterra.financetrackerv2.utils.SharedPrefsUtil

class SplashActivity : AppCompatActivity() {

    companion object {
        const val SPLASH_TIME: Long = 1500
    }

    //SharedPrefs Manager
    private lateinit var sharedPrefsUtil: SharedPrefsUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initSharedPrefs()

        handleSplashActivity()

    }

    private fun initSharedPrefs(){

        sharedPrefsUtil = SharedPrefsUtil(this)
        //sharedPrefsUtil.nukeSharedPrefs()

    }

    private fun isUserFirstTime():Boolean{

        return sharedPrefsUtil.getUserName() == null

    }

    private fun handleSplashActivity() {

        Handler().postDelayed({

            if (isUserFirstTime()){
                startActivity(Intent(this, ChatActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

        }, SPLASH_TIME)

    }


}