package com.res.restotrack

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.app.AlertDialog


class LogOut : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_out)



        val logoutButton = findViewById<Button>(R.id.logout)

        logoutButton.setOnClickListener{
            val intent = Intent(this, FirstLogin::class.java)
            startActivity(intent)
        }

        val button_logout = findViewById<Button>(R.id.logout)
        button_logout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") {_, _ ->
                    val goto_logout = Intent(this,FirstLogin::class.java)
                    startActivity(goto_logout)
                }
                .setNegativeButton("No",null)
                .show()
        }

    }
}