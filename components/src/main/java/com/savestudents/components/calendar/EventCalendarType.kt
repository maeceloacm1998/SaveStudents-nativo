package com.savestudents.components.calendar

import androidx.annotation.DrawableRes
import com.savestudents.components.R
import com.savestudents.core.notificationservice.models.EventType

enum class EventCalendarType(@DrawableRes val drawableInt: Int) {
    MATTER(R.drawable.bg_rounded_calendar_type_matter),
    EVENT(R.drawable.bg_rounded_calendar_type_event);

    companion object {
        fun parse(value: String): EventCalendarType {
            return when(value) {
                EventType.EVENT.value -> EVENT
                else -> MATTER
            }
        }
    }
}