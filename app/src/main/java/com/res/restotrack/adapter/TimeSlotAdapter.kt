package com.res.restotrack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.res.restotrack.data.TimeSlot

class TimeSlotAdapter(
    private val timeSlots: List<TimeSlot>,
    private val onLongClickListener: (Int) -> Unit = {},
    private val onClickListener: (Int) -> Unit = {}
) : RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder>() {

    inner class TimeSlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeSlotText: TextView = itemView.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSlotViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return TimeSlotViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimeSlotViewHolder, position: Int) {
        val timeSlot = timeSlots[position]
        holder.timeSlotText.text = "${timeSlot.startTime} - ${timeSlot.endTime}"

        holder.itemView.setOnLongClickListener {
            onLongClickListener(position)
            true
        }

        holder.itemView.setOnClickListener {
            onClickListener(position)
        }
    }

    override fun getItemCount(): Int = timeSlots.size
}