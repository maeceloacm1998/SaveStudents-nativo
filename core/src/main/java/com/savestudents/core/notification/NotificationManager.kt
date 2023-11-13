package com.savestudents.core.notification

interface NotificationManager {
    fun createChannel(idChannel: String, name: String)
    fun builderNotification(
        idChannel: String,
        title: String,
        deepLink: String,
        description: String,
        drawable: Int,
        idNotification: Int
    )

    fun openAppNotificationSettings()
    fun isNotificationEnabled(): Boolean
}