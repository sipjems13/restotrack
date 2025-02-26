package com.res.restotrack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast


class LoginActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val button_register =findViewById<Button>(R.id.button_register)
        button_register.setOnClickListener {
            Log.e("CSIT284","Clicked!")
            Toast.makeText(this, "Button is clicked", Toast.LENGTH_SHORT).show()

            val intent = Intent(this,Landing::class.java )
            startActivity(intent)
        }
    }
}

