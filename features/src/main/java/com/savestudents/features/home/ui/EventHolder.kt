package com.savestudents.features.home.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.savestudents.features.databinding.EventHolderBinding
import com.savestudents.features.addMatter.models.Event

class EventHolder(private val binding: EventHolderBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Event) {
        val eventAdapter = EventItemAdapter()
        binding.apply {
            dayName.text = item.dayName
            eventsRv.run {
                adapter = eventAdapter
                layoutManager = LinearLayoutManager(eventsRv.context)
            }
//            eventAdapter.submitList(item.events)
        }
    }
}
