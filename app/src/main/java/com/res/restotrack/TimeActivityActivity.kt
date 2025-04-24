package com.res.restotrack

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.res.restotrack.adapter.TimeSlotAdapter
import com.res.restotrack.application.TimeSlotApp
import com.res.restotrack.data.TimeSlot
import com.res.restotrack.viewmodel.TimeSlotViewModel

class TimeActivityActivity : Activity() {
    private lateinit var viewModel: TimeSlotViewModel
    private lateinit var adapter: TimeSlotAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_activity)

        viewModel = TimeSlotViewModel()
        recyclerView = findViewById(R.id.recyclerView)
        addButton = findViewById(R.id.addButton)

        setupRecyclerView()
        setupAddButton()
    }

    private fun showSelectionConfirmation(position: Int) {
        val timeSlot = viewModel.getTimeSlots()[position]
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_time_slot_confirmation, null)

        dialogView.findViewById<TextView>(R.id.timeSlot).text =
            "${timeSlot.startTime} - ${timeSlot.endTime}"

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.positiveButton).setOnClickListener {
            // Handle booking confirmation
            Toast.makeText(this, "Time slot booked!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.negativeButton).setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setWindowAnimations(R.style.DialogAnimation)
        dialog.show()
    }

    private fun setupRecyclerView() {
        adapter = TimeSlotAdapter(viewModel.getTimeSlots()) { position ->
            showDeleteConfirmation(position)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupAddButton() {
        addButton.setOnClickListener {
            addNewTimeSlot()
        }
    }

    private fun addNewTimeSlot() {
        val lastSlot = if (TimeSlotApp.timeSlots.isNotEmpty()) {
            TimeSlotApp.timeSlots.last()
        } else {
            TimeSlot(0, "8:00 AM", "10:00 AM")
        }

        // Parse the last end time
        val (timePart, ampm) = lastSlot.endTime.split(" ")
        val (hours, minutes) = timePart.split(":").map { it.toInt() }

        // Calculate new start time (30 minutes after last end time)
        var newHours = hours
        var newMinutes = minutes + 30
        var newAMPM = ampm

        if (newMinutes >= 60) {
            newHours += 1
            newMinutes -= 60
        }

        if (newHours >= 12) {
            if (newHours > 12) newHours -= 12
            newAMPM = if (ampm == "AM") "PM" else "AM"
        }

        val newStartTime = String.format("%d:%02d %s", newHours, newMinutes, newAMPM)

        // Calculate end time (2 hours after start time)
        var endHours = newHours + 2
        var endMinutes = newMinutes
        var endAMPM = newAMPM

        if (endHours >= 12) {
            if (endHours > 12) endHours -= 12
            endAMPM = if (newAMPM == "AM") "PM" else "AM"
        }

        val newEndTime = String.format("%d:%02d %s", endHours, endMinutes, endAMPM)

        viewModel.addTimeSlot(newStartTime, newEndTime)
        adapter.notifyItemInserted(TimeSlotApp.timeSlots.size - 1)
        recyclerView.smoothScrollToPosition(TimeSlotApp.timeSlots.size - 1)
    }

    private fun showDeleteConfirmation(position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Delete Time Slot")
            .setMessage("Are you sure you want to delete this time slot?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.removeTimeSlot(position)
                adapter.notifyItemRemoved(position)
                Toast.makeText(this, "Time slot deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}