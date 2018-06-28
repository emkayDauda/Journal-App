package com.alc.askemkay.journalapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)

        val mainActivity = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(mainActivity)
        finish()
    }
}
