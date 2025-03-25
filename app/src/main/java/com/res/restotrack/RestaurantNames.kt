package com.res.restotrack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

class RestaurantNames : Activity() {
    private lateinit var reservedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_names)

        val listView = findViewById<ListView>(R.id.restaurant_view)
        val dateTextView = TextView(this).apply {
            textSize = 20f
            setPadding(16, 16, 16, 16)
        }

        // Add date to the ListView header
        listView.addHeaderView(dateTextView)

        // Get the reserved date from the intent
        reservedDate = intent.getStringExtra("RESERVED_DATE") ?: "No date selected"
        dateTextView.text = "Reserved Date: $reservedDate"

        // Add more restaurants for scrollable list
        val restaurants = listOf(
            "The Golden Spoon - Cebu City - ⭐ 4.5",
            "La Vista - Mandaue - ⭐ 4.2",
            "Sea Breeze - Lapu-Lapu - ⭐ 4.8",
            "Casa Verde - Cebu IT Park - ⭐ 4.7",
            "The Hungry Plate - Talisay - ⭐ 4.3",
            "Sakura Sushi - Mactan - ⭐ 4.6",
            "Lantaw Native - Busay - ⭐ 4.4",
            "Cafe Laguna - Ayala Mall - ⭐ 4.5",
            "Abaca Baking Company - Cebu IT Park - ⭐ 4.8",
            "Isla Sugbu Seafood City - SM Seaside - ⭐ 4.7",
            "The Pyramid - Cebu City - ⭐ 4.3",
            "Pungko-pungko sa Fuente - Fuente Osmeña - ⭐ 4.1"
        )

        // Connect the list to the ListView using ArrayAdapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, restaurants)
        listView.adapter = adapter

        // Handle item clicks
        listView.setOnItemClickListener { _, _, position, _ ->
            if (position == 0) return@setOnItemClickListener  // Skip header

            val selectedRestaurant = restaurants[position - 1]  // Offset by 1 due to header
            Toast.makeText(this, "Selected: $selectedRestaurant", Toast.LENGTH_SHORT).show()

            // Send the selected restaurant and date back to Landing
            val resultIntent = Intent().apply {
                putExtra("RESERVED_DATE", reservedDate)
                putExtra("SELECTED_RESTAURANT", selectedRestaurant)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()  // Go back to Landing
        }
    }
}
