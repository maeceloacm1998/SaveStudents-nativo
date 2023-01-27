package br.oficial.savestudents.model.repository

import br.oficial.savestudents.model.NotificationTimeline
import br.oficial.savestudents.service.external.model.FirebaseResponseModel

interface ITimelineSettingsRepository {
    fun setNotificationTimeline(documentPath: String, notificationItem: NotificationTimeline)
    fun deleteNotificationTimeline(id: String)
    fun existNotificationTimeline(id: String, firebaseResponseModel: FirebaseResponseModel<NotificationTimeline>)
}