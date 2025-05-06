package com.res.restotrack

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.res.restotrack.utils.SessionManager

class Setting : Activity() {
    private lateinit var backButton: Button
    private lateinit var logoutButton: Button
    private lateinit var cancelButton: Button
    private lateinit var confirmLogoutButton: Button
    private lateinit var sessionManager: SessionManager

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        sessionManager = SessionManager(this)

        val Setdev = findViewById<Button>(R.id.setdev)
        backButton = findViewById(R.id.back_button)
        logoutButton = findViewById(R.id.btnLogout)

        backButton.setOnClickListener {
            finish()
        }

        Setdev.setOnClickListener {
            val intent = Intent(this, DeveloperPage::class.java)
            startActivity(intent)
        }

        logoutButton.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        setContentView(R.layout.activity_log_out)
        
        cancelButton = findViewById(R.id.btnCancel)
        confirmLogoutButton = findViewById(R.id.logout)

        cancelButton.setOnClickListener {
            setContentView(R.layout.activity_setting)
            initializeViews()
        }

        confirmLogoutButton.setOnClickListener {
            // Clear session and navigate to login
            sessionManager.clearSession()
            
            val intent = Intent(this, FillLogin::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun initializeViews() {
        val Setdev = findViewById<Button>(R.id.setdev)
        backButton = findViewById(R.id.back_button)
        logoutButton = findViewById(R.id.btnLogout)

        backButton.setOnClickListener {
            finish()
        }

        Setdev.setOnClickListener {
            val intent = Intent(this, DeveloperPage::class.java)
            startActivity(intent)
        }

        logoutButton.setOnClickListener {
            showLogoutDialog()
        }
    }
}