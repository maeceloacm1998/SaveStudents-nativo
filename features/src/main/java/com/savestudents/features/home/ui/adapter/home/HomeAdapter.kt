package com.savestudents.features.home.ui.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.savestudents.features.databinding.EventHolderBinding
import com.savestudents.features.addMatter.models.Event

class HomeAdapter : ListAdapter<Event, EventHolder>(HomeDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val binding = EventHolderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EventHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(getItem(position) as Event)
    }
}

class HomeDiffCallback : DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return newItem == oldItem
    }

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return true
    }
}