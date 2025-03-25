package com.res.restotrack

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.app.AlertDialog

class Landing : Activity() {

    companion object {
        const val REQUEST_CODE_RESERVE = 1
    }

    private lateinit var reservedDateTextView: TextView

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_activty)

        // Initialize UI Components
        val pbut = findViewById<ImageButton>(R.id.pbutton)
        val settingsButton = findViewById<ImageButton>(R.id.settingsbutton)
        val logoutButton = findViewById<ImageButton>(R.id.logoutButton)
        val calendarButton = findViewById<ImageButton>(R.id.calendar)  // Calendar button
        reservedDateTextView = findViewById(R.id.reservedDateTextView)  // Date display TextView

        // Set default text
        reservedDateTextView.text = "No date reserved"

        // Profile Navigation
        pbut.setOnClickListener {
            Log.e("CSIT284", "Clicked!")
            Toast.makeText(this, "Button is clicked", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

        // Settings Navigation
        settingsButton.setOnClickListener {
            Log.e("Landing", "Settings button clicked!")
            Toast.makeText(this, "Opening Settings", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, Setting::class.java)
            startActivity(intent)
        }

        // Logout Confirmation
        logoutButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { _, _ ->
                    val gotoLogout = Intent(this, LoginActivity::class.java)
                    startActivity(gotoLogout)
                    finish()  // Closes current activity
                }
                .setNegativeButton("No", null)
                .show()
        }

        // Calendar Reservation Navigation
        calendarButton.setOnClickListener {
            val calendarIntent = Intent(this, CalendarReservation::class.java)
            startActivityForResult(calendarIntent, REQUEST_CODE_RESERVE)
        }
    }

    // Handle Result from CalendarReservation
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_RESERVE && resultCode == Activity.RESULT_OK) {
            val reservedDate = data?.getStringExtra("RESERVED_DATE")

            if (!reservedDate.isNullOrEmpty()) {
                reservedDateTextView.text = "Reserved Date: $reservedDate"
                Toast.makeText(this, "Date Reserved: $reservedDate", Toast.LENGTH_SHORT).show()
            } else {
                reservedDateTextView.text = "No date reserved"
            }
        }
    }
}
