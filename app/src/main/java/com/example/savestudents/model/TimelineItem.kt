package com.example.savestudents.model

data class TimelineItem(
    var subjectsInformation: SubjectList = SubjectList(),
    var timelineList: MutableList<CreateTimelineItem> = mutableListOf(),
)