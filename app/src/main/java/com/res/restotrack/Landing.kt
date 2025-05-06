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
    private lateinit var restaurantNameTextView: TextView
    private lateinit var restaurantCuisineTextView: TextView

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        val PButton = findViewById<ImageButton>(R.id.PButton) // Calendar button
        reservedDateTextView = findViewById(R.id.reservedDateTextView)  // Date display TextView
        restaurantNameTextView = findViewById(R.id.restaurantNameTextView)  // Restaurant name TextView
        restaurantCuisineTextView = findViewById(R.id.restaurantCuisineTextView)  // Restaurant cuisine TextView

        // Profile Navigation
        PButton.setOnClickListener {
            Log.e("CSIT284", "Clicked!")
            Toast.makeText(this, "Button is clicked", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, HomePage::class.java)
            startActivityForResult(intent, REQUEST_CODE_RESERVE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_RESERVE && resultCode == RESULT_OK && data != null) {
            val restaurantName = data.getStringExtra("restaurantName")
            val restaurantCuisine = data.getStringExtra("restaurantCuisine")
            val reservationDate = data.getStringExtra("reservationDate")

            // Update the UI with reservation details
            reservedDateTextView.text = "Reservation Date: $reservationDate"
            restaurantNameTextView.text = "Restaurant: $restaurantName"
            restaurantCuisineTextView.text = "Cuisine: $restaurantCuisine"
        }
    }
}




