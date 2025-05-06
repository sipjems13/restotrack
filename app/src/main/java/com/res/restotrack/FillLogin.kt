package com.res.restotrack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuthException
import com.res.restotrack.utils.SessionManager

class FillLogin : Activity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_login)

        auth = Firebase.auth
        sessionManager = SessionManager(this)

        // Check if user is already logged in
        if (sessionManager.isLoggedIn() && auth.currentUser != null) {
            // User is already logged in, go to Landing
            startActivity(Intent(this, Landing::class.java))
            finish()
            return
        }

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

            // Direct Firebase authentication without reCAPTCHA
            signInWithFirebase(email, passwordInput)
        }
    }

    private fun signInWithFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Get current user and save session with UID
                    val user = auth.currentUser
                    if (user != null) {
                        sessionManager.saveLoginSession(email, user.uid)
                        
                        // Login success
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Landing::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Login failed: User not found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val exception = task.exception
                    if (exception is FirebaseAuthException) {
                        when (exception.errorCode) {
                            "ERROR_TOO_MANY_ATTEMPTS_TRY_LATER" -> {
                                Toast.makeText(this, "Too many attempts. Please try again later.", Toast.LENGTH_LONG).show()
                            }
                            else -> {
                                Toast.makeText(this, "Login failed: ${exception.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
