package com.savestudents.features.addMatter.models

data class Schedule(
    val userId: String = "",
    val data: List<Event> = mutableListOf()
)