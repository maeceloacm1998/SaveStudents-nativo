package com.example.data_transfer.dto

import com.example.data_transfer.model.CreateTimelineItem
import com.example.data_transfer.model.NotificationTimeline

data class NotificationTimelineDTO(
    val id: String = "",
    val deeplink: String = "",
    val pushToken: String = "",
    val subjectName: String = "",
    val shift: String = "",
    var timelineList: MutableList<CreateTimelineItem>? = null
)

fun NotificationTimelineDTO.asDomainModel() = NotificationTimeline(
    id = this.id,
    deeplink = this.deeplink,
    pushToken = this.pushToken,
    subjectName = this.subjectName,
    shift = this.shift,
    timelineList = this.timelineList
)
