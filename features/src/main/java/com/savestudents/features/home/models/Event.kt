package com.savestudents.features.home.models

data class Event(
    val dayName: String = "",
    val events: List<EventItem> = mutableListOf()
) {
    data class EventItem(
        val date: String = "",
        val eventName: String = "",
        val description: String = "",
        val hours: String = "",
    )
}