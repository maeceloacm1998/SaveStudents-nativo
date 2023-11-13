package com.savestudents.features.notificationconfig.ui

import com.savestudents.core.notification.NotificationCustomManager

class NotificationConfigPresenter(
    private val view: NotificationConfigContract.View,
    private val notificationCustomManager: NotificationCustomManager
) : NotificationConfigContract.Presenter {
    override fun start() {}

    override fun handleNotification() {
        notificationCustomManager.openAppNotificationSettings()
    }

    override fun checkNotificationResult() {
        if (notificationCustomManager.isNotificationEnabled()) {
            view.enabledNotificationSwitch()
        } else {
            view.disabledNotificationSwitch()
        }
    }
}