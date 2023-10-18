package com.savestudents.features.event.ui

import com.savestudents.features.mvp.BasePresenter
import com.savestudents.features.mvp.BaseView

interface EventContract {
    interface View : BaseView<Presenter> {
        fun loading(loading: Boolean)
        fun showEventNameError()
        fun hideEventNameError()
        fun showDateSelectedError()
        fun hideDateSelectedError()
        fun showSnackBarSuccess(eventName: String)
        fun showSnackBarError(eventName: String)
        fun goToCurriculum()
    }

    interface Presenter : BasePresenter {
        suspend fun validateEvent(eventName: String, date: Long?)
    }
}
