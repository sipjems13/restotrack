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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.res.restotrack.utils.SessionManager

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
    private lateinit var auth: FirebaseAuth
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = Firebase.auth
        sessionManager = SessionManager(this)

        // Check if user is logged in
        if (!sessionManager.isLoggedIn() || auth.currentUser == null) {
            startActivity(Intent(this, FillLogin::class.java))
            finish()
            return
        }

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
                    // Sign out from Firebase
                    auth.signOut()
                    // Clear session
                    sessionManager.clearSession()
                    val gotoLogout = Intent(this, FillLogin::class.java)
                    startActivity(gotoLogout)
                    finish()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    private fun loadProfileData() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val savedUserName = sharedPreferences.getString("username", currentUser.displayName ?: "User")
            val savedUserEmail = sharedPreferences.getString("useremail", currentUser.email ?: "")

            userNameTextView.text = savedUserName
            userEmailTextView.text = savedUserEmail
        }
    }

    @SuppressLint("WrongViewCast")
    private fun saveProfileChanges() {
        val newUserName = editUserName.text.toString().trim()
        val newUserEmail = editEmail.text.toString().trim()

        if (newUserName.isEmpty() || newUserEmail.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val currentUser = auth.currentUser
        if (currentUser != null) {
            // Update only display name in Firebase
            val profileUpdates = com.google.firebase.auth.UserProfileChangeRequest.Builder()
                .setDisplayName(newUserName)
                .build()

            currentUser.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Save both name and email to SharedPreferences
                        saveToSharedPreferences(newUserName, newUserEmail)
                    } else {
                        Toast.makeText(this, "Failed to update profile: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun saveToSharedPreferences(userName: String, userEmail: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", userName)
        editor.putString("useremail", userEmail)
        editor.apply()

        userNameTextView.text = userName
        userEmailTextView.text = userEmail

        editUserName.text.clear()
        editEmail.text.clear()

        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
    }
}
