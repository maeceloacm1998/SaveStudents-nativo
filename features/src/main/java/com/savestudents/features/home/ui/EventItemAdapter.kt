package com.savestudents.features.home.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.savestudents.features.addMatter.models.EventType
import com.savestudents.features.addMatter.models.Matter
import com.savestudents.features.databinding.EventItemBinding

class EventItemAdapter(
    private var matterName: String = "",
    private var eventType: EventType = EventType.MATTER,
    private var initialTime: String = "",
    private var timelineList: MutableList<Matter.Timeline> = mutableListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = EventItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EventItemHolder(binding = binding)
    }

    override fun getItemCount(): Int {
        return timelineList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as EventItemHolder).bind(
            matterName = matterName,
            eventType = eventType,
            initialTime = initialTime,
            timeline = timelineList[position]
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(
        newMatterName: String,
        newEventType: EventType,
        newInitialTime: String,
        newTimeline: Matter.Timeline
    ) {
        matterName = newMatterName
        eventType = newEventType
        initialTime = newInitialTime
        timelineList.add(newTimeline)

        notifyDataSetChanged()
    }

    fun emptyTimelineList() {
        timelineList = mutableListOf()
        notifyDataSetChanged()
    }
}