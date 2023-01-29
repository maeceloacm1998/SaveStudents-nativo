package com.example.data_transfer.model

data class NotificationTimeline(
    val id: String = "",
    val deeplink: String = "",
    val pushToken: String = "",
    val subjectName: String = "",
    val shift: String = "",
    var timelineList: MutableList<CreateTimelineItem>? = null
)