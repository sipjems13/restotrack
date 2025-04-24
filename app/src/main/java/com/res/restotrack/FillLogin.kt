package com.res.restotrack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class FillLogin : Activity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_login)

        auth = FirebaseAuth.getInstance()

        val et_username = findViewById<EditText>(R.id.username)
        val et_password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.loginButton_2)

        val username = intent.getStringExtra("username") ?: ""
        val password = intent.getStringExtra("password") ?: ""

        if (username.isNotEmpty()) et_username.setText(username)
        if (password.isNotEmpty()) et_password.setText(password)

        login.setOnClickListener {
            val email = et_username.text.toString()
            val passwordInput = et_password.text.toString()

            if (email.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, passwordInput)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Landing::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
