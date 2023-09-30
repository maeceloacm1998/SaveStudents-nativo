package com.savestudents.features.home.ui

import androidx.recyclerview.widget.RecyclerView
import com.savestudents.core.utils.DateUtils
import com.savestudents.features.addMatter.models.EventType
import com.savestudents.features.addMatter.models.Matter
import com.savestudents.features.databinding.EventItemBinding

class EventItemHolder(private val binding: EventItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(matterName: String, eventType: EventType, initialTime: String, timeline: Matter.Timeline) {
        val (_, month, day) = DateUtils.extractYearMonthDay(timeline.date)

        binding.apply {
            dayText.text = day.toString()
            monthText.text = DateUtils.getMonthName(month)
            eventTitle.text = matterName
            eventDescription.text = handleEventDescription(matterName, eventType)
            hour.text = initialTime
        }
    }

    private fun handleEventDescription(matterName: String, eventType: EventType): String {
        return when(eventType) {
            EventType.EVENT -> ""
            EventType.MATTER -> ""
        }
    }
}