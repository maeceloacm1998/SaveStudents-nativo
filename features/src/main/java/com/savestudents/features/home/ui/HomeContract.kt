package com.savestudents.features.home.ui

import com.savestudents.features.addMatter.models.Event
import com.savestudents.core.mvp.BasePresenter
import com.savestudents.core.mvp.BaseView

interface HomeContract {

    interface View : BaseView<Presenter> {
        fun onLoading(loading: Boolean)
        fun onSetupViewsHomeAdapter()
        fun onSetEventList(eventList: List<Event>)
    }

    interface Presenter : BasePresenter {
        suspend fun handleEvents()
    }
}