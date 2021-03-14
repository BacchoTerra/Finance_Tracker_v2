package com.bacchoterra.financetrackerv2.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import com.bacchoterra.financetrackerv2.R

class SplashActivity : AppCompatActivity() {

    companion object {
        const val SPLASH_TIME: Long = 1500
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        finishSplashActivity()

    }

    private fun finishSplashActivity() {

        Handler().postDelayed({
            startActivity(Intent(this, ChatActivity::class.java))
        }, SPLASH_TIME)

    }

}