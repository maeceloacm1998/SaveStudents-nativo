package com.savestudents.features.curriculum.ui

import com.savestudents.features.addMatter.models.Event
import com.savestudents.features.mvp.BasePresenter
import com.savestudents.features.mvp.BaseView

interface CurriculumContract {
    interface View : BaseView<Presenter> {
        fun error()
        fun loadingScreen(visibility: Boolean)
        fun showNotEvents(visibility: Boolean)
        fun calendarExpanded()
        fun calendarCollapsed()
        fun setEvent(year: Int, month: Int, day: Int)
        fun updateEventList(eventList: List<Event.EventItem>, day: Int, month: Int)
    }

    interface Presenter : BasePresenter {
        suspend fun fetchMatters(month: Int)
        suspend fun fetchEventsWithDate(timestamp: Long)
    }
}