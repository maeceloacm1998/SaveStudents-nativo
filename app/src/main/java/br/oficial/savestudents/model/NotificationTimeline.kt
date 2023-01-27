package br.oficial.savestudents.model

data class NotificationTimeline(
    val id: String = "",
    val pushToken: String = "",
    val subjectName: String = "",
    val shift: String = "",
    var timelineList: MutableList<CreateTimelineItem>? = null
)