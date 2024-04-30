package com.savestudents.features.home.ui.adapter.eventItem

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.savestudents.core.utils.DateUtils
import com.savestudents.features.addMatter.models.Event
import com.savestudents.features.databinding.EventItemBinding

class EventItemHolder(private val binding: EventItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        eventItem: Event.EventItem,
        day: Int,
        month: Int,
        showDelete: Boolean = false,
        clickDeleteEventListener: ((item: Event.EventItem) -> Unit)?,
    ) {
        binding.apply {
            chType.text = eventItem.type
            tvDayText.text = day.toString()
            tvMonthText.text = DateUtils.getMonthName(month)
            tvEventTitle.text = eventItem.matterName
            tvEventPeriod.text = eventItem.period
            tvHour.text = eventItem.initialTime

            btDelete.apply {
                isVisible = showDelete
                setOnClickListener {
                    clickDeleteEventListener?.let { listener -> listener(eventItem) }
                }
            }
        }
    }
}