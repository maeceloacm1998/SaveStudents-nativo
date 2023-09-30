package com.savestudents.features.home.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.savestudents.features.addMatter.models.Event
import com.savestudents.features.databinding.EventItemBinding

class EventItemAdapter(
    private var eventList: List<Event.EventItem> = mutableListOf(),
    private var day: Int = 0,
    private var month: Int = 0
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = EventItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EventItemHolder(binding = binding)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as EventItemHolder).bind(eventItem = eventList[position], day = day, month = month)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(eventList: List<Event.EventItem>, day: Int, month: Int) {
        this.eventList = eventList
        this.day = day
        this.month = month

        notifyDataSetChanged()
    }
}