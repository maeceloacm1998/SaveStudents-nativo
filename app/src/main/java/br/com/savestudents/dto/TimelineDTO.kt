package br.com.savestudents.dto

import br.com.savestudents.model.CreateTimelineItem
import br.com.savestudents.model.SubjectList
import br.com.savestudents.model.TimelineItem

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