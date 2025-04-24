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

class Setting : Activity() {
    private lateinit var backButton: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val Setdev = findViewById<Button>(R.id.setdev)

        backButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        Setdev.setOnClickListener {
            val intent = Intent(this, DeveloperPage::class.java)
            startActivity(intent)
        }

    }
}