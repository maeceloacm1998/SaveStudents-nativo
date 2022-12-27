package br.oficial.savestudents.model

data class TimelineItem(
    var subjectsInformation: SubjectList? = null,
    var timelineList: MutableList<CreateTimelineItem>? = null,
)