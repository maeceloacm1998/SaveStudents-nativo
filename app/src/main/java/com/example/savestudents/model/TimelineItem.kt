package com.example.savestudents.model

data class TimelineItem(
    var subjectsInformation: SubjectList? = null,
    var timelineList: MutableList<CreateTimelineItem>? = null,
)