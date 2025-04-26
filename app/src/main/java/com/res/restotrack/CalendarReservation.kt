package com.res.restotrack

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import android.util.Log

class CalendarReservation : Activity() {
    private var reservedDate: String = ""
    private var selectedYear: Int = 0
    private var selectedMonth: Int = 0
    private var selectedDay: Int = 0
    private lateinit var closeButton: ImageButton
    private lateinit var reservationsListView: ListView
    private lateinit var reservationsAdapter: ArrayAdapter<String>
    private val reservationsList = mutableListOf<String>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_reservation)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val tvCurrentDate = findViewById<TextView>(R.id.idTVDate)

        // Dynamically add Reserve Date button
        val reserveButton = Button(this).apply {
            text = "Reserve Date"
            setOnClickListener {
                // Handle button click
                onReserveDateClick()
            }
        }

        closeButton = findViewById(R.id.CloseC)
        closeButton.setOnClickListener {
            finish()
        }

        // Add button to the layout
        val layout = findViewById<LinearLayout>(R.id.main)
        layout.addView(reserveButton)

        // Initialize the ListView for showing the reservations
        reservationsListView = findViewById(R.id.reservationsListView)
        reservationsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, reservationsList)
        reservationsListView.adapter = reservationsAdapter

        // Set the default text for the TextView
        tvCurrentDate.text = "Select Date"

        // Date selection handler for the CalendarView
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedYear = year
            selectedMonth = month
            selectedDay = dayOfMonth

            reservedDate = "${month + 1}/$dayOfMonth/$year"
            tvCurrentDate.text = reservedDate
            Toast.makeText(this, "Selected Date: $reservedDate", Toast.LENGTH_SHORT).show()
        }

        // Load existing reservations when the screen is opened
        loadReservations()
    }

    private fun onReserveDateClick() {
        // Check if the date selected is valid
        if (isDateValid()) {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                val userId = user.uid
                val firestore = FirebaseFirestore.getInstance()

                // Create a reference to store the reservation
                val reservationRef = firestore.collection("users")
                    .document(userId)
                    .collection("reservations")
                    .document()  // Automatically generates a unique ID for the reservation

                // Prepare the reservation data
                val reservationData = mapOf(
                    "reservedDate" to reservedDate,
                    "timestamp" to System.currentTimeMillis()
                )

                Log.d("Firestore", "Saving reservation for user: $userId")
                Log.d("Firestore", "Data to save: $reservationData")

                // Save reservation data to Firestore
                reservationRef.set(reservationData).addOnSuccessListener {
                    // Immediately update the list view with the new reservation
                    reservationsList.add(reservedDate) // Add the new reserved date to the list
                    reservationsAdapter.notifyDataSetChanged() // Notify the adapter that the data has changed
                    Toast.makeText(this, "Date reserved successfully!", Toast.LENGTH_SHORT).show()

                    // Optionally, you can return the reserved date to the previous activity
                    val resultIntent = Intent().apply {
                        putExtra("RESERVED_DATE", reservedDate)
                    }
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to reserve date.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Cannot reserve a past date!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadReservations() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val userId = user.uid
            val firestore = FirebaseFirestore.getInstance()
            val reservationRef = firestore.collection("users")
                .document(userId)
                .collection("reservations")

            reservationRef.get().addOnSuccessListener { documents ->
                reservationsList.clear() // Clear the existing list before adding new data
                for (document in documents) {
                    val reservedDate = document.getString("reservedDate")
                    reservedDate?.let {
                        reservationsList.add(it)
                    }
                }
                reservationsAdapter.notifyDataSetChanged() // Update the ListView with new data
            }.addOnFailureListener {
                Toast.makeText(this@CalendarReservation, "Failed to load reservations.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isDateValid(): Boolean {
        val currentCalendar = Calendar.getInstance()
        val selectedCalendar = Calendar.getInstance().apply {
            set(selectedYear, selectedMonth, selectedDay)
        }

        return !selectedCalendar.before(currentCalendar) // Ensure the selected date is not in the past
    }
}
