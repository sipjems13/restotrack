package com.res.restotrack

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.res.restotrack.adapter.RestaurantAdapter
import com.res.restotrack.data.Restaurant
import java.text.SimpleDateFormat
import java.util.*

class RestaurantList : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var selectedDate: Long = 0
    private val database = FirebaseDatabase.getInstance()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_list)

        auth = FirebaseAuth.getInstance()

        // Check if user is logged in
        if (auth.currentUser == null) {
            Toast.makeText(this, "Please log in to make a reservation", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

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
                name = "Sugbo Mercado",
                cuisine = "Local",
                rating = 4.5f,
                status = "Open",
                imageUrl = "https://images.squarespace-cdn.com/content/v1/5d7f2d797a64971f017f10ff/2acc09ba-cb96-468f-ad17-2de6a0f55606/03-02+SUGBO+MERCADO+2023+CP.png?format=2500w"
            ),
            Restaurant(
                id = "2",
                name = "Mott 32",
                cuisine = "Chinese ",
                rating = 4.8f,
                status = "Open",
                imageUrl = "https://nustar.ph/wp-content/uploads/2023/12/Mott-32-40-2048x1366.jpg"
            ),
            Restaurant(
                id = "3",
                name = "Yappari Steak",
                cuisine = "Japan",
                rating = 4.2f,
                status = "Closed",
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9BqT3mogc1Qn1s5zg-1Iw23BX7tPWwC3jXNhLOTowh5YNDXMziddUtihlLtqW6Zj7AmY&usqp=CAU"
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

        val userId = auth.currentUser?.uid ?: return

        // Create a unique reservation ID
        val reservationId = database.reference.child("reservations").push().key ?: return

        val reservation = hashMapOf(
            "restaurantName" to restaurant.name,
            "restaurantCuisine" to restaurant.cuisine,
            "reservationDate" to formattedDate,
            "timestamp" to selectedDate,
            "userId" to userId
        )

        // Save under user's reservations
        database.reference.child("users")
            .child(userId)
            .child("reservations")
            .child(reservationId)
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