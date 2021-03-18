package com.bacchoterra.financetrackerv2.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.bacchoterra.financetrackerv2.R
import com.bacchoterra.financetrackerv2.utils.SharedPrefsUtil

class SplashActivity : AppCompatActivity() {

    //SharedPreferences manager
    private lateinit var sharedPrefsUtil: SharedPrefsUtil

    companion object {
        const val SPLASH_TIME: Long = 1500
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initObjects()

        handleSplashActivity()

    }

    private fun initObjects() {
        sharedPrefsUtil = SharedPrefsUtil(this)
    }

    private fun handleSplashActivity() {


        Handler().postDelayed({
            handleNextMove()

        }, SPLASH_TIME)

    }

    private fun startChatActivity() {
        startActivity(Intent(this, ChatActivity::class.java))
        finish()
    }

    private fun isUserFirstTimeHere(): Boolean {

        return sharedPrefsUtil.getUserName() == null

    }

    private fun hasPassword(): Boolean {

        return sharedPrefsUtil.getPassword() != null

    }

    private fun handleNextMove() {
        if (isUserFirstTimeHere()) {
            startChatActivity()
        } else {
            if (hasPassword()) {
                Toast.makeText(this, "has password", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "no password", Toast.LENGTH_SHORT).show()
            }
        }
    }

}