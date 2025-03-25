package com.res.restotrack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import java.util.Calendar

class CalendarReservation : Activity() {
    private var reservedDate: String = ""
    private var selectedYear: Int = 0
    private var selectedMonth: Int = 0
    private var selectedDay: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_reservation)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val tvCurrentDate = findViewById<TextView>(R.id.idTVDate)
        val reserveButton = Button(this).apply {
            text = "Reserve Date"
        }

        // Add button dynamically
        val layout = findViewById<android.widget.LinearLayout>(R.id.main)
        layout.addView(reserveButton)

        // Set default date
        tvCurrentDate.text = "Select Date"

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedYear = year
            selectedMonth = month  // Month is 0-based
            selectedDay = dayOfMonth

            reservedDate = "${month + 1}/$dayOfMonth/$year"
            tvCurrentDate.text = reservedDate
            Toast.makeText(this, "Selected Date: $reservedDate", Toast.LENGTH_SHORT).show()
        }

        // Handle Reserve Button Click
        reserveButton.setOnClickListener {
            if (isDateValid()) {
                val resultIntent = Intent().apply {
                    putExtra("RESERVED_DATE", reservedDate)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()  // Close the CalendarReservation properly
            } else {
                Toast.makeText(this, "Cannot reserve a past date!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to check if the selected date is today or later
    private fun isDateValid(): Boolean {
        val currentCalendar = Calendar.getInstance()
        val selectedCalendar = Calendar.getInstance().apply {
            set(selectedYear, selectedMonth, selectedDay)
        }

        return !selectedCalendar.before(currentCalendar)  // Check if the date is today or later
    }
}
