package com.res.restotrack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*

class CalendarReservation : Activity() {
    private var reservedDate: String = ""
    private var selectedYear: Int = 0
    private var selectedMonth: Int = 0
    private var selectedDay: Int = 0

    private lateinit var calendarView: CalendarView
    private lateinit var reserveButton: Button
    private lateinit var reservationsListView: ListView
    private lateinit var reservationsAdapter: ArrayAdapter<String>
    private val reservationsList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_reservation)

        calendarView = findViewById(R.id.calendarView)
        reserveButton = findViewById(R.id.reserveButton)
        reservationsListView = findViewById(R.id.reservationsListView)

        val tvCurrentDate = findViewById<TextView>(R.id.idTVDate)
        tvCurrentDate.text = "Select Date"

        reservationsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, reservationsList)
        reservationsListView.adapter = reservationsAdapter

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
                saveReservation()
            } else {
                Toast.makeText(this, "Cannot reserve a past date!", Toast.LENGTH_SHORT).show()
            }
        }

        loadReservations()
    }

    private fun saveReservation() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val userId = user.uid
            val database = FirebaseDatabase.getInstance().reference
            val reservationRef = database.child("users").child(userId).child("reservations").push()

            val reservationData = mapOf(
                "reservedDate" to reservedDate,
                "timestamp" to System.currentTimeMillis()
            )

            reservationRef.setValue(reservationData).addOnSuccessListener {
                reservationsList.add(reservedDate)
                reservationsAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Date reserved!", Toast.LENGTH_SHORT).show()

                // Navigate to CuisineListActivity after saving
                val intent = Intent(this, CuisineListActivity::class.java)
                startActivity(intent)

            }.addOnFailureListener {
                Toast.makeText(this, "Reservation failed.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun loadReservations() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val userId = user.uid
            val database = FirebaseDatabase.getInstance().reference
            val reservationsRef = database.child("users").child(userId).child("reservations")

            reservationsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    reservationsList.clear()
                    for (reservationSnapshot in snapshot.children) {
                        val date = reservationSnapshot.child("reservedDate").getValue(String::class.java)
                        if (!date.isNullOrEmpty()) {
                            reservationsList.add(date)
                        }
                    }
                    reservationsAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@CalendarReservation, "Failed to load reservations.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun isDateValid(): Boolean {
        val current = Calendar.getInstance()
        val selected = Calendar.getInstance().apply {
            set(selectedYear, selectedMonth, selectedDay)
        }
        return !selected.before(current)
    }
}
