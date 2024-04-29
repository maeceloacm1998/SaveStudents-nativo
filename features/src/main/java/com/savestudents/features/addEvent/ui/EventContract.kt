package com.savestudents.features.addEvent.ui

import com.savestudents.core.mvp.BasePresenter
import com.savestudents.core.mvp.BaseView

interface EventContract {
    interface View : BaseView<Presenter> {
        fun onLoading(loading: Boolean)
        fun onSetupViewsCreateEventButton()
        fun onSetupViewsSelectedDate()
        fun showEventNameError()
        fun hideEventNameError()
        fun showDateSelectedError()
        fun hideDateSelectedError()
        fun showSnackBarSuccess(eventName: String)
        fun showSnackBarError(eventName: String)
        fun goToCurriculum()
    }

    interface Presenter : BasePresenter {
        suspend fun handleValidateEvent(eventName: String, dateSelected: Long?)
    }
}
