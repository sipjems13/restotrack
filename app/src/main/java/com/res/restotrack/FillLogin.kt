package com.res.restotrack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class FillLogin : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_login)

        val et_username = findViewById<EditText>(R.id.username)
        val et_password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.loginButton_2)


        val username = intent.getStringExtra("username") ?: ""
        val password = intent.getStringExtra("password") ?: ""


        if (username.isNotEmpty()) et_username.setText(username)
        if (password.isNotEmpty()) et_password.setText(password)

        login.setOnClickListener {
            if (et_username.text.toString().isEmpty() || et_password.text.toString().isEmpty()) {
                Toast.makeText(this, "Please put your information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, Landing::class.java)
            startActivity(intent)
        }
    }
}
