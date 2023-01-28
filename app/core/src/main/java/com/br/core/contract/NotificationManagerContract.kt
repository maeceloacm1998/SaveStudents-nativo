package com.br.core.contract

interface NotificationManagerContract {
    fun createChannel(idChannel: String, name: String)
    fun builderNotification(idChannel: String, title: String, description: String, drawable: Int, idNotification: Int)
    fun openAppNotificationSettings()
    fun isNotificationEnabled(): Boolean
    fun getPushToken()
}