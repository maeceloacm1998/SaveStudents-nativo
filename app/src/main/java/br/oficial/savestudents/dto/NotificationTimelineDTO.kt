package br.oficial.savestudents.dto

import br.oficial.savestudents.model.CreateTimelineItem
import br.oficial.savestudents.model.NotificationTimeline

data class NotificationTimelineDTO(
    val id: String = "",
    val pushToken: String = "",
    val subjectName: String = "",
    val shift: String = "",
    var timelineList: MutableList<CreateTimelineItem>? = null
)

fun NotificationTimelineDTO.asDomainModel() = NotificationTimeline(
    id = this.id,
    pushToken = this.pushToken,
    subjectName = this.subjectName,
    shift = this.shift,
    timelineList = this.timelineList
)
