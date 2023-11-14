package com.savestudents.core.notificationservice.models

data class Schedule(
    val userId: String = "",
    val data: List<Event> = mutableListOf()
)