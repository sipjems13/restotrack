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

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)


        val PButton = findViewById<ImageButton>(R.id.PButton) // Calendar button
        reservedDateTextView = findViewById(R.id.reservedDateTextView)  // Date display TextView




        // Profile Navigation
        PButton.setOnClickListener {
            Log.e("CSIT284", "Clicked!")
            Toast.makeText(this, "Button is clicked", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }
    }
}




