package com.savestudents.components.calendar

import android.view.View
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.view.ViewContainer
import com.savestudents.components.databinding.CalendarCustomDayBinding
import com.savestudents.components.databinding.CalendarCustomViewBinding
import java.time.LocalDate

class DayViewContainer(
    view: View,
    calendarBinding: CalendarCustomViewBinding,
    selectedDate: LocalDate?
) : ViewContainer(view) {
    lateinit var day: CalendarDay
    val binding = CalendarCustomDayBinding.bind(view)

    private var onClickCalendarItem: (day: LocalDate) -> Unit = {}

    init {
        setupViews(selectedDate, calendarBinding)
    }

    private fun setupViews(selectedDate: LocalDate?, calendarBinding: CalendarCustomViewBinding) {
        view.setOnClickListener {
            if (day.position == DayPosition.MonthDate) {
                if (selectedDate != day.date) {
                    calendarBinding.exFiveCalendar.notifyDateChanged(day.date)
                    onClickCalendarItem(day.date)
                }
            }
        }
    }

    fun onClickCalendarListener(listener: (day: LocalDate) -> Unit) {
        onClickCalendarItem = listener
    }
}