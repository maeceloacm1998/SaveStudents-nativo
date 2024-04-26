package com.savestudents.features.home.ui

import com.savestudents.features.addMatter.models.Event
import com.savestudents.core.mvp.BasePresenter
import com.savestudents.core.mvp.BaseView

interface HomeContract {

    interface View : BaseView<Presenter> {
        fun loading(loading: Boolean)
        fun setEventList(eventList: List<Event>)
    }

    interface Presenter : BasePresenter {
        suspend fun getEvents()
    }
}