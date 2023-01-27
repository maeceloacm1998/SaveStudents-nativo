package com.example.data_transfer.model.repository

import com.example.data_transfer.model.NotificationTimeline

interface ITimelineSettingsRepository {
    fun setNotificationTimeline(documentPath: String, notificationItem: NotificationTimeline)
    fun deleteNotificationTimeline(id: String)
    fun existNotificationTimeline(id: String, firebaseResponseModel: FirebaseResponseModel<NotificationTimeline>)
}