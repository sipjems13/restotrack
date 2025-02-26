package com.res.restotrack

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import android.app.AlertDialog

class Landing : Activity() {
    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_activty)
        /*setContentView(R.layout.profile)*/
        val pbut = findViewById<ImageButton>(R.id.pbutton)


        pbut.setOnClickListener {
            Log.e("CSIT284","Clicked!")
            Toast.makeText(this, "Button is clicked", Toast.LENGTH_SHORT).show()

            val intent = Intent(this,Profile::class.java )
            startActivity(intent)
        }


        val settingsButton = findViewById<ImageButton>(R.id.settingsbutton)
        settingsButton.setOnClickListener {
            Log.e("Landing", "Settings button clicked!")
            Toast.makeText(this, "Opening Settings", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, Setting::class.java)
            startActivity(intent)
        }

        val logoutButton = findViewById<ImageButton>(R.id.logoutButton)
        logoutButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { _, _ ->
                    val gotoLogout = Intent(this, LoginActivity::class.java)
                    startActivity(gotoLogout)
                    finish() // Closes the current activity
                }
                .setNegativeButton("No", null)
                .show()
        }


        }
    }