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

        val layout = findViewById<android.widget.LinearLayout>(R.id.main)
        layout.addView(reserveButton)

        tvCurrentDate.text = "Select Date"

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedYear = year
            selectedMonth = month
            selectedDay = dayOfMonth

            reservedDate = "${month + 1}/$dayOfMonth/$year"
            tvCurrentDate.text = reservedDate
            Toast.makeText(this, "Selected Date: $reservedDate", Toast.LENGTH_SHORT).show()
        }

        reserveButton.setOnClickListener {
            if (isDateValid()) {
                val intent = Intent(this, RestaurantNames::class.java).apply {
                    putExtra("RESERVED_DATE", reservedDate)
                }
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Cannot reserve a past date!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isDateValid(): Boolean {
        val currentCalendar = Calendar.getInstance()
        val selectedCalendar = Calendar.getInstance().apply {
            set(selectedYear, selectedMonth, selectedDay)
        }

        return !selectedCalendar.before(currentCalendar)
    }
}
