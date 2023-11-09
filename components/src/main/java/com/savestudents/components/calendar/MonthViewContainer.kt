package com.savestudents.components.calendar

import android.view.View
import com.kizitonwose.calendar.view.ViewContainer
import com.savestudents.components.databinding.CalendarCustomHeaderBinding

class MonthViewContainer(view: View) : ViewContainer(view) {
    val bindingLegend = CalendarCustomHeaderBinding.bind(view).legendLayout.root
}