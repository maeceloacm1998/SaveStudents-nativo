package com.savestudents.features.curriculum.ui.models

import com.savestudents.features.addMatter.models.Event.EventItem

data class CurriculumEventCalendar (
    val events: List<EventItem> = mutableListOf()
)