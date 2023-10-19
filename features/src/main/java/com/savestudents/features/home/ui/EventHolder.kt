package com.savestudents.features.home.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.savestudents.core.utils.DateUtils
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
        }

        val (year, month, day) = handleEvents(item.dayName)
        eventAdapter.updateData(item.events, day, month)
    }

    private fun handleEvents(dayName: String): Triple<Int, Int, Int> {
        val dateOfWeek: List<Long> = DateUtils.getWeekDatesTimestamp(DateUtils.getCurrentDate())
        var date: Triple<Int, Int, Int> = Triple(0, 0, 0)

        dateOfWeek.forEach { weekDate ->
            if (DateUtils.getDayOfWeekFromTimestamp(weekDate) == dayName) {
                date = DateUtils.getDateWithTimestamp(weekDate)
            }
        }

        return date
    }
}
