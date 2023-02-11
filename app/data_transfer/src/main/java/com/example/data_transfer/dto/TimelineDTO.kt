package com.example.data_transfer.dto

import com.example.data_transfer.model.CreateTimelineItem
import com.example.data_transfer.model.SubjectList
import com.example.data_transfer.model.TimelineItem

data class TimelineDTO(
    var subjectsInformation: SubjectList,
    var timelineList: MutableList<CreateTimelineItem> = mutableListOf(CreateTimelineItem()),
)

fun TimelineDTO.asModel(): TimelineItem {
    return TimelineItem(
        subjectsInformation = this.subjectsInformation,
        timelineList = this.timelineList
    )
}

fun List<TimelineDTO>.asDomainModel(): List<TimelineItem> {
    return this.map { item ->
        TimelineItem(
            subjectsInformation = item.subjectsInformation,
            timelineList = item.timelineList
        )
    }
}