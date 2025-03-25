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

    private lateinit var reservedInfoTextView: TextView

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_activty)

        val pbut = findViewById<ImageButton>(R.id.pbutton)
        val settingsButton = findViewById<ImageButton>(R.id.settingsbutton)
        val logoutButton = findViewById<ImageButton>(R.id.logoutButton)
        val calendarButton = findViewById<ImageButton>(R.id.calendar)
        reservedInfoTextView = findViewById(R.id.reservedInfoTextView)

        reservedInfoTextView.text = "No reservation made"

        pbut.setOnClickListener {
            Log.e("CSIT284", "Clicked!")
            Toast.makeText(this, "Button is clicked", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

        settingsButton.setOnClickListener {
            Log.e("Landing", "Settings button clicked!")
            Toast.makeText(this, "Opening Settings", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, Setting::class.java)
            startActivity(intent)
        }

        logoutButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { _, _ ->
                    val gotoLogout = Intent(this, LoginActivity::class.java)
                    startActivity(gotoLogout)
                    finish()
                }
                .setNegativeButton("No", null)
                .show()
        }

        calendarButton.setOnClickListener {
            val calendarIntent = Intent(this, CalendarReservation::class.java)
            startActivityForResult(calendarIntent, REQUEST_CODE_RESERVE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_RESERVE && resultCode == Activity.RESULT_OK) {
            val reservedDate = data?.getStringExtra("RESERVED_DATE") ?: "No date"
            val selectedRestaurant = data?.getStringExtra("SELECTED_RESTAURANT") ?: "No restaurant"

            reservedInfoTextView.text = "Reserved: $reservedDate\nPlace: $selectedRestaurant"
            Toast.makeText(this, "Reservation Successful!", Toast.LENGTH_SHORT).show()
        }
    }
}
