package com.savestudents.features.home.ui.adapter.home

import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.savestudents.core.utils.DateUtils
import com.savestudents.features.databinding.EventHolderBinding
import com.savestudents.features.addMatter.models.Event
import com.savestudents.features.home.ui.adapter.eventItem.EventItemAdapter

class EventHolder(private val binding: EventHolderBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Event) {
        val eventAdapter = EventItemAdapter(showDelete = false, clickDeleteEventListener = null)
        binding.apply {
            dayName.text = item.dayName
            eventsRv.run {
                adapter = eventAdapter
                layoutManager = LinearLayoutManager(eventsRv.context)
            }
        }

        if(item.events.isEmpty()) {
            showCalendarError()
        } else {
            hideCalendarError()
            val (day, month, _) = handleEvents(item.dayName)
            eventAdapter.updateData(item.events, day, month)
        }
    }

    private fun showCalendarError() {
        binding.calendarError.container.isVisible = true
    }

    private fun hideCalendarError() {
        binding.calendarError.container.isVisible = false
    }

    private fun handleEvents(dayName: String): Triple<Int, Int, Int> {
        val dateOfWeek: List<Long> = DateUtils.getWeekDatesTimestamp()
        var date: Triple<Int, Int, Int> = Triple(EMPTY_YEAR, EMPTY_MONTH, EMPTY_DAY)

        dateOfWeek.forEach { weekDate ->
            if (DateUtils.getDayOfWeekFromTimestamp(weekDate) == dayName) {
                date = DateUtils.getDateWithTimestamp(weekDate)
            }
        }

        return date
    }

    companion object {
        const val EMPTY_DAY = 0
        const val EMPTY_MONTH = 0
        const val EMPTY_YEAR = 0
    }
}
