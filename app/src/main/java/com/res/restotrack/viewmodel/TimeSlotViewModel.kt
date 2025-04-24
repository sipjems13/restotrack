package com.res.restotrack.viewmodel

import androidx.lifecycle.ViewModel
import com.res.restotrack.application.TimeSlotApp
import com.res.restotrack.data.TimeSlot

class TimeSlotViewModel : ViewModel() {
    fun getTimeSlots(): List<TimeSlot> {
        return TimeSlotApp.timeSlots
    }

    fun addTimeSlot(startTime: String, endTime: String) {
        val newId = if (TimeSlotApp.timeSlots.isEmpty()) 1 else TimeSlotApp.timeSlots.last().id + 1
        TimeSlotApp.timeSlots.add(TimeSlot(newId, startTime, endTime))
    }

    fun removeTimeSlot(position: Int) {
        if (position in 0 until TimeSlotApp.timeSlots.size) {
            TimeSlotApp.timeSlots.removeAt(position)
        }
    }
}