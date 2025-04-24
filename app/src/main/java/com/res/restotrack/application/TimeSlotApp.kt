package com.res.restotrack.application

import android.app.Application
import com.res.restotrack.data.TimeSlot

class TimeSlotApp : Application(){

    companion object {
        lateinit var timeSlots: MutableList<TimeSlot>
    }

    override fun onCreate() {
        super.onCreate()
        // Initialize with some default time slots
        timeSlots = mutableListOf(
            TimeSlot(1, "10:00 AM", "12:00 PM"),
            TimeSlot(2, "12:30 PM", "2:30 PM"),
            TimeSlot(3, "3:00 PM", "5:00 PM")
        )
    }
}
