package com.savestudents.core.notificationservice.models

data class Event(
    val dayName: String = "",
    var events: MutableList<EventItem> = mutableListOf()
) {
    data class EventItem(
        val id: String = "",
        val type: String = "",
        val matterName: String = "",
        val period: String? = "",
        val initialTime: String? = "",
        val timestamp: Long? = 0L
    )
}