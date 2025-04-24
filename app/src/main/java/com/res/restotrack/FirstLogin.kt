package com.res.restotrack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button


class FirstLogin : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_login)

        val loginButton = findViewById<Button>(R.id.login)
        val signUpButton = findViewById<Button>(R.id.signup)

        loginButton.setOnClickListener{
            val intent = Intent(this, FillLogin::class.java)
            startActivity(intent)
        }
        signUpButton.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }

    }
}