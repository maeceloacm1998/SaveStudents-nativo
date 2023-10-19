package com.savestudents.features.addMatter.models

data class Event(
    val dayName: String = "",
    val events: MutableList<EventItem> = mutableListOf()
) {
    data class EventItem(
        val id: String = "",
        val type: String = "",
        val matterName: String = "",
        val period: String? = "",
        val initialTime: String? = "",
        val finalTime: String? = "",
        val timestamp: Long? = 0L
    )
}