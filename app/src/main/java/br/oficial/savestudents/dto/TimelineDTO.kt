package br.oficial.savestudents.dto

import br.oficial.savestudents.model.CreateTimelineItem
import br.oficial.savestudents.model.SubjectList
import br.oficial.savestudents.model.TimelineItem

data class TimelineDTO(
    var subjectsInformation: SubjectList = SubjectList(),
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