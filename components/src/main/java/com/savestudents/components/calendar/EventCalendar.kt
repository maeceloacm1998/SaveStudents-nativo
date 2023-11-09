package com.savestudents.components.calendar

import java.time.LocalDate

data class EventCalendar (
    val date: LocalDate,
    val eventCalendarType: List<EventCalendarType>,
)