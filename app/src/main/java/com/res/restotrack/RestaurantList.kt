package com.res.restotrack

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.res.restotrack.adapter.RestaurantAdapter
import com.res.restotrack.data.Restaurant
import java.text.SimpleDateFormat
import java.util.*

class RestaurantList : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var selectedDate: Long = 0
    private val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_list)

        // Get the selected date from the intent
        selectedDate = intent.getLongExtra("selectedDate", 0)
        if (selectedDate == 0L) {
            Toast.makeText(this, "Error: No date selected", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.restaurantRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Sample data - replace with your actual data source
        val sampleRestaurants = listOf(
            Restaurant(
                id = "1",
                name = "The Gourmet Kitchen",
                cuisine = "International",
                rating = 4.5f,
                status = "Open",
                imageUrl = "https://example.com/restaurant1.jpg"
            ),
            Restaurant(
                id = "2",
                name = "Sushi Master",
                cuisine = "Japanese",
                rating = 4.8f,
                status = "Open",
                imageUrl = "https://example.com/restaurant2.jpg"
            ),
            Restaurant(
                id = "3",
                name = "Pasta Paradise",
                cuisine = "Italian",
                rating = 4.2f,
                status = "Closed",
                imageUrl = "https://example.com/restaurant3.jpg"
            )
        )

        // Set up the adapter
        val adapter = RestaurantAdapter(sampleRestaurants) { restaurant ->
            // Save reservation to Firebase
            saveReservation(restaurant)
        }

        recyclerView.adapter = adapter
    }

    private fun saveReservation(restaurant: Restaurant) {
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(Date(selectedDate))

        val reservation = hashMapOf(
            "restaurantName" to restaurant.name,
            "restaurantCuisine" to restaurant.cuisine,
            "reservationDate" to formattedDate,
            "timestamp" to selectedDate
        )

        // Get current user ID from your authentication system
        val userId = "current_user_id" // Replace with actual user ID

        database.reference.child("reservations")
            .child(userId)
            .setValue(reservation)
            .addOnSuccessListener {
                // Pass data back to Landing activity
                val intent = intent.apply {
                    putExtra("restaurantName", restaurant.name)
                    putExtra("restaurantCuisine", restaurant.cuisine)
                    putExtra("reservationDate", formattedDate)
                    putExtra("timestamp", selectedDate)
                }
                setResult(RESULT_OK, intent)
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to save reservation: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
} 