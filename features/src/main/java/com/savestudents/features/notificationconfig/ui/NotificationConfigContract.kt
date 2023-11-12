package com.savestudents.features.notificationconfig.ui

import com.savestudents.features.mvp.BasePresenter
import com.savestudents.features.mvp.BaseView

interface NotificationConfigContract {

    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter {
    }
}