package com.savestudents.components.calendar

import androidx.annotation.DrawableRes
import com.savestudents.components.R

enum class EventCalendarType(@DrawableRes val drawableInt: Int) {
    MATTER(R.drawable.bg_rounded_calendar_type_matter),
    EVENT(R.drawable.bg_rounded_calendar_type_event)
}