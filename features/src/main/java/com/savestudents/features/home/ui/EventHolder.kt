package com.savestudents.features.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.savestudents.features.databinding.EventHolderBinding
import com.savestudents.features.databinding.EventItemBinding
import com.savestudents.features.addMatter.models.Event

class EventHolder(private val binding: EventHolderBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Event) {
        val eventAdapter = EventAdapter()
        binding.apply {
            dayName.text = item.dayName
            eventsRv.run {
                adapter = eventAdapter
                layoutManager = LinearLayoutManager(eventsRv.context)
            }
            eventAdapter.submitList(item.events)
        }
    }
}

class EventItemHolder(private val binding: EventItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Event.EventItem) {
        binding.apply {
            day.text = item.date.toString()
            month.text = "maio"
            eventTitle.text = item.matter?.matterName
            eventDescription.text = ""
            hour.text = item.initialTime
        }
    }
}

class EventAdapter : ListAdapter<Event.EventItem, EventItemHolder>(EventDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventItemHolder {
        val binding = EventItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EventItemHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: EventItemHolder, position: Int) {
        holder.bind(getItem(position) as Event.EventItem)
    }
}

class EventDiffCallback : DiffUtil.ItemCallback<Event.EventItem>() {
    override fun areItemsTheSame(oldItem: Event.EventItem, newItem: Event.EventItem): Boolean {
        return newItem == oldItem
    }

    override fun areContentsTheSame(oldItem: Event.EventItem, newItem: Event.EventItem): Boolean {
        return true
    }
}