package com.savestudents.features.notificationconfig.ui

import com.savestudents.core.notification.NotificationManager

class NotificationConfigPresenter(
    private val view: NotificationConfigContract.View,
    private val notificationManager: NotificationManager
) : NotificationConfigContract.Presenter {
    override fun start() {}

    override fun handleNotification() {
        notificationManager.openAppNotificationSettings()
    }

    override fun checkNotificationResult() {
        if (notificationManager.isNotificationEnabled()) {
            view.enabledNotificationSwitch()
        } else {
            view.disabledNotificationSwitch()
        }
    }
}