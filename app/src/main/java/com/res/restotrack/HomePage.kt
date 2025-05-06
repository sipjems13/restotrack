package com.res.restotrack

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class HomePage : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var proceedButton: MaterialButton
    private var selectedDate: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        // Initialize views
        calendarView = findViewById(R.id.calendarView)
        proceedButton = findViewById(R.id.Yes)

        // Set up calendar listener
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // Convert to milliseconds
            val calendar = java.util.Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.timeInMillis
        }

        // Set up proceed button click listener
        proceedButton.setOnClickListener {
            if (selectedDate > 0) {
                val intent = Intent(this, RestaurantList::class.java)
                intent.putExtra("selectedDate", selectedDate)
                startActivityForResult(intent, 1)
            } else {
                Toast.makeText(this, "Please select a date first", Toast.LENGTH_SHORT).show()
            }
        }

        // Set up bottom navigation click listeners
        setupBottomNavigation()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Pass the data back to Landing activity
            setResult(RESULT_OK, data)
            finish()
        }
    }

    private fun setupBottomNavigation() {
        // Profile navigation
        findViewById<View>(R.id.navProfile).setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

        // Home navigation
        findViewById<View>(R.id.navHome).setOnClickListener {
            // Already on home, do nothing
        }

        // Settings navigation
        findViewById<View>(R.id.navSettings).setOnClickListener {
            val intent = Intent(this, Setting::class.java)
            startActivity(intent)
        }
    }
}
