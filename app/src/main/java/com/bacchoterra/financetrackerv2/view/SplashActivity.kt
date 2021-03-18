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

    //Layout components
    private lateinit var binder:ActivitySplashBinding

    //SharedPreferences manager
    private lateinit var sharedPrefsUtil: SharedPrefsUtil

    companion object {
        const val SPLASH_TIME: Long = 1500
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binder.root)
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

    private fun handleNextMove() {
        if (isUserFirstTimeHere()) {
            startChatActivity()
        } else {
            if (hasPassword()) {
                enableUserToEnterPassword()
            } else {
                startMainActivity()
            }
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
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

    private fun enableUserToEnterPassword(){

        binder.activitySplashInputLayout.visibility = View.VISIBLE
        binder.activitySplashFabCheck.visibility = View.VISIBLE

        checkPassword()

    }

    private fun checkPassword(){

        binder.activitySplashFabCheck.setOnClickListener {

            val password = sharedPrefsUtil.getPassword()
            val input = binder.activitySplashEditPassword.text.toString()

            if(input == password){
                startMainActivity()
            }else{
                binder.activitySplashInputLayout.error = getString(R.string.wrong_pasword)
            }

        }

    }

}