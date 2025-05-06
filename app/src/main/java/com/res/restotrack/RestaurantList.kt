package com.res.restotrack

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RestaurantList : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var selectedDate: Long = 0

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

        // TODO: Initialize your restaurant adapter and set it to the RecyclerView
        // TODO: Load restaurant data from your data source
    }
} 