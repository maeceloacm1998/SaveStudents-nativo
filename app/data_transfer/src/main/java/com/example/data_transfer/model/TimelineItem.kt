package com.example.data_transfer.model

data class TimelineItem(
    var subjectsInformation: SubjectList? = null,
    var timelineList: MutableList<CreateTimelineItem>? = null,
)