package com.alc.askemkay.journalapp.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth


class SplashActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)

        mAuth = FirebaseAuth.getInstance()


        if (mAuth.currentUser == null){
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        } else {
            val mainActivity = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(mainActivity)
            finish()
        }


    }
}
