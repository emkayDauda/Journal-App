package com.alc.askemkay.journalapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.view.View
import android.widget.Button

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v){
            mSignInButton ->{

            }
        }

    }

    private lateinit var mEmailField: TextInputEditText
    private lateinit var mPasswordField: TextInputEditText
    private lateinit var mSignUpButton: Button
    private lateinit var mSignInButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initializeComponents()

    }

    private fun initializeComponents(){
        mEmailField = findViewById(R.id.emailField)
        mPasswordField = findViewById(R.id.passwordField)
        mSignInButton = findViewById(R.id.signInButton)
        mSignUpButton = findViewById(R.id.signUpButton)

    }
}
