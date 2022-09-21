package com.example.savestudents.dto

import com.example.savestudents.model.CreateTimelineItem
import com.example.savestudents.model.SubjectList
import com.example.savestudents.model.TimelineItem

data class TimelineDTO(
    var subjectsInformation: SubjectList? = SubjectList(),
    var timelineList: MutableList<CreateTimelineItem>? = mutableListOf(CreateTimelineItem()),
)

fun List<TimelineDTO>.asDomainModel(): List<TimelineItem> {
    return this.map { item ->
        TimelineItem(
            subjectsInformation = item.subjectsInformation,
            timelineList = item.timelineList
        )
    }
}