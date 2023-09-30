package com.savestudents.features.curriculum.ui

import com.savestudents.features.addMatter.models.EventType
import com.savestudents.features.addMatter.models.Matter
import com.savestudents.features.mvp.BasePresenter
import com.savestudents.features.mvp.BaseView

interface CurriculumContract {
    interface View : BaseView<Presenter> {
        fun setEvent(year: Int, month: Int, day: Int)
        fun updateEventList(
            matterName: String,
            eventType: EventType,
            initialTime: String,
            timelineList: Matter.Timeline
        )
    }

    interface Presenter : BasePresenter {
        suspend fun fetchMatters()
        suspend fun fetchEventsWithDate(timestamp: Long)
    }
}