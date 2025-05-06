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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Landing : Activity() {

    companion object {
        const val REQUEST_CODE_RESERVE = 1
    }

    private lateinit var reservedDateTextView: TextView
    private lateinit var restaurantNameTextView: TextView
    private lateinit var restaurantCuisineTextView: TextView
    private lateinit var auth: FirebaseAuth
    private val database = FirebaseDatabase.getInstance()

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        auth = FirebaseAuth.getInstance()

        // Check if user is logged in
        if (auth.currentUser == null) {
            startActivity(Intent(this, FillLogin::class.java))
            finish()
            return
        }

        val PButton = findViewById<ImageButton>(R.id.PButton) // Calendar button
        reservedDateTextView = findViewById(R.id.reservedDateTextView)  // Date display TextView
        restaurantNameTextView = findViewById(R.id.restaurantNameTextView)  // Restaurant name TextView
        restaurantCuisineTextView = findViewById(R.id.restaurantCuisineTextView)  // Restaurant cuisine TextView

        // Load user's reservations
        loadUserReservations()

        // Profile Navigation
        PButton.setOnClickListener {
            Log.e("CSIT284", "Clicked!")
            Toast.makeText(this, "Button is clicked", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, HomePage::class.java)
            startActivityForResult(intent, REQUEST_CODE_RESERVE)
        }
    }

    private fun loadUserReservations() {
        val userId = auth.currentUser?.uid ?: return

        database.reference.child("users")
            .child(userId)
            .child("reservations")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        // Get the most recent reservation
                        val reservations = snapshot.children.toList()
                        if (reservations.isNotEmpty()) {
                            val latestReservation = reservations.maxByOrNull { 
                                it.child("timestamp").getValue(Long::class.java) ?: 0L 
                            }

                            latestReservation?.let { reservation ->
                                val restaurantName = reservation.child("restaurantName").getValue(String::class.java)
                                val restaurantCuisine = reservation.child("restaurantCuisine").getValue(String::class.java)
                                val reservationDate = reservation.child("reservationDate").getValue(String::class.java)

                                // Update UI with reservation details
                                reservedDateTextView.text = "Reservation Date: $reservationDate"
                                restaurantNameTextView.text = "Restaurant: $restaurantName"
                                restaurantCuisineTextView.text = "Cuisine: $restaurantCuisine"
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Landing, "Failed to load reservations", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_RESERVE && resultCode == RESULT_OK && data != null) {
            // Reload reservations to show the new one
            loadUserReservations()
        }
    }
}




