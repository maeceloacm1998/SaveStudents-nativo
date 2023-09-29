package com.savestudents.features.accountRegister.ui

import com.savestudents.features.addMatter.models.DaysType
import com.savestudents.features.addMatter.models.Event
import com.savestudents.features.addMatter.models.Schedule

object ScheduleData {
    val schedule: Schedule = Schedule(
        userId = "",
        data = mutableListOf(
            Event(
                dayName = DaysType.MONDAY.value
            ),
            Event(
                dayName = DaysType.TUESDAY.value
            ),
            Event(
                dayName = DaysType.WEDNESDAY.value
            ),
            Event(
                dayName = DaysType.THURSDAY.value
            ),
            Event(
                dayName = DaysType.FRIDAY.value
            ),
            Event(
                dayName = DaysType.SATURDAY.value
            )
        )
    )
}