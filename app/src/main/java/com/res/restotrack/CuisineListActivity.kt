package com.res.restotrack

import android.app.Activity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.res.restotrack.CuisineAdapter


class CuisineListActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_cuisine)

        val listView = findViewById<ListView>(R.id.cuisineListView)

        val cuisines = listOf(
            Cuisine("Italian", "$$"),
            Cuisine("Mexican", "$$$"),
            Cuisine("Japanese", "$$$"),
            Cuisine("Chinese", "$"),
            Cuisine("French", "$$$$"),
            Cuisine("Indian", "$$")
        )

        val adapter = CuisineAdapter(this, cuisines)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedCuisine = cuisines[position]
            Toast.makeText(this, "Selected: ${selectedCuisine.name}", Toast.LENGTH_SHORT).show()
        }
    }
}
