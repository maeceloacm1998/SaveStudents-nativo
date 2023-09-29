package com.savestudents.features.addMatter.models

data class Event(
    val dayName: String = "",
    val events: MutableList<EventItem> = mutableListOf()
) {
    data class EventItem(
        val date: Long = 0,
        val type: String = "",
        val matter: Matter? = null,
        val initialTime: String = "",
        val finalTime: String = "",
    )
}