package com.savestudents.features.home.ui

import androidx.recyclerview.widget.RecyclerView
import com.savestudents.features.databinding.EventHolderBinding
import com.savestudents.features.home.models.Event

class EventHolder(private val binding: EventHolderBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Event) {
        binding.apply {
            text.text = item.dayName
        }
    }
}