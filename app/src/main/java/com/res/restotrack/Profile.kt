package com.res.restotrack

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class Profile : Activity() {

    private lateinit var profilePicture: ImageView
    private lateinit var userNameTextView: TextView
    private lateinit var userEmailTextView: TextView
    private lateinit var editUserName: EditText
    private lateinit var editEmail: EditText
    private lateinit var saveButton: TextView
    private lateinit var logoutButton: Button
    private lateinit var backButton: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        profilePicture = findViewById(R.id.profile_picture)
        userNameTextView = findViewById(R.id.user_name)
        userEmailTextView = findViewById(R.id.user_email)
        editUserName = findViewById(R.id.edit_user_name)
        editEmail = findViewById(R.id.edit_email)
        saveButton = findViewById(R.id.save_button)
        logoutButton = findViewById(R.id.logout_button)
        backButton = findViewById(R.id.back_button)

        sharedPreferences = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)

        loadProfileData()

        saveButton.setOnClickListener {
            saveProfileChanges()
        }

        backButton.setOnClickListener {
            finish()
        }
        logoutButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { _, _ ->
                    val gotoLogout = Intent(this, LoginActivity::class.java)
                    startActivity(gotoLogout)
                    finish() // Closes the current activity
                }
                .setNegativeButton("No", null)
                .show()
        }

    }

    private fun loadProfileData() {
        val savedUserName = sharedPreferences.getString("username", "Myron Alia")
        val savedUserEmail = sharedPreferences.getString("useremail", "myronalia@gmail.com")

        userNameTextView.text = savedUserName
        userEmailTextView.text = savedUserEmail
    }

    @SuppressLint("WrongViewCast")
    private fun saveProfileChanges() {
        val newUserName = editUserName.text.toString().trim()
        val newUserEmail = editEmail.text.toString().trim()

        if (newUserName.isEmpty() || newUserEmail.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val editor = sharedPreferences.edit()
        editor.putString("username", newUserName)
        editor.putString("useremail", newUserEmail)
        editor.apply()

        userNameTextView.text = newUserName
        userEmailTextView.text = newUserEmail

        editUserName.text.clear()
        editEmail.text.clear()

        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()


    }
}
