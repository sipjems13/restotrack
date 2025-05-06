package com.res.restotrack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.res.restotrack.utils.SessionManager

class LoginActivity : Activity() {
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize SessionManager
        sessionManager = SessionManager(this)
        
        // Check if user is already logged in
        if (sessionManager.isLoggedIn()) {
            // User is already logged in, go to Landing
            startActivity(Intent(this, Landing::class.java))
            finish()
            return
        }
        
        setContentView(R.layout.activity_login)
        val button_register = findViewById<Button>(R.id.button_register)
        button_register.setOnClickListener {
            Log.e("CSIT284","Clicked!")
            Toast.makeText(this, "Button is clicked", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, Landing::class.java)
            startActivity(intent)
        }
    }
}

