package com.savestudents.features.notificationconfig.ui

import com.savestudents.core.mvp.BasePresenter
import com.savestudents.core.mvp.BaseView

interface NotificationConfigContract {

    interface View : BaseView<Presenter> {
        fun enabledNotificationSwitch()
        fun disabledNotificationSwitch()
    }

    interface Presenter : BasePresenter {
        fun handleNotification()
        fun checkNotificationResult()
    }
}