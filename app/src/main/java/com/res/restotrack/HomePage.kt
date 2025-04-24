package com.res.restotrack

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.res.restotrack.Landing.Companion.REQUEST_CODE_RESERVE


class HomePage : Activity() {

    companion object {
        const val REQUEST_CODE_RESERVE = 1
    }

    private lateinit var reservedDateTextView: TextView

    @SuppressLint("MissingInflatedId", "WrongViewCast")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val settingsButton = findViewById<ImageButton>(R.id.settingsbuttonHP)
        val usersButton = findViewById<ImageButton>(R.id.usersbuttonHP)
        val yes = findViewById<Button>(R.id.Yes)


        settingsButton.setOnClickListener{
            val intent = Intent(this, Setting::class.java)
            startActivity(intent)
        }
        usersButton.setOnClickListener{
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }
        yes.setOnClickListener{
            val calendarIntent = Intent(this, CalendarReservation::class.java)
            startActivityForResult(calendarIntent, REQUEST_CODE_RESERVE)
        }


       fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            Toast.makeText(this, "Selected: $dayOfMonth-${month + 1}-$year", Toast.LENGTH_SHORT)
                .show()

        }
    }
}
