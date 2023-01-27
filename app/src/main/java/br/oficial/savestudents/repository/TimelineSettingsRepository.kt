package br.oficial.savestudents.repository

import com.br.core.constants.FirestoreDbConstants
import com.example.data_transfer.dto.NotificationTimelineDTO
import com.example.data_transfer.dto.asDomainModel
import com.br.core.service.external.FirebaseClient
import com.example.data_transfer.model.NotificationTimeline
import com.example.data_transfer.model.OnFailureModel
import com.example.data_transfer.model.repository.FirebaseResponseModel
import com.example.data_transfer.model.repository.ITimelineSettingsRepository
import com.google.firebase.firestore.DocumentSnapshot

class TimelineSettingsRepository : ITimelineSettingsRepository {
    private val client = FirebaseClient()

    override fun setNotificationTimeline(
        documentPath: String, notificationItem: NotificationTimeline
    ) {
        client.setSpecificDocument(FirestoreDbConstants.Collections.NOTIFICATION_TIMELINE_LIST,
            documentPath,
            notificationItem,
            object : FirebaseResponseModel<Boolean> {
                override fun onSuccess(model: Boolean) {}

                override fun onFailure(error: OnFailureModel) {}
            })
    }

    override fun deleteNotificationTimeline(
        id: String
    ) {
        client.deleteDocument(FirestoreDbConstants.Collections.NOTIFICATION_TIMELINE_LIST, id)
    }

    override fun existNotificationTimeline(
        id: String,
        firebaseResponseModel: FirebaseResponseModel<NotificationTimeline>
    ) {
        client.getSpecificDocument(FirestoreDbConstants.Collections.NOTIFICATION_TIMELINE_LIST,
            id,
            object : FirebaseResponseModel<DocumentSnapshot> {
                override fun onSuccess(model: DocumentSnapshot) {
                    val res = model.toObject(NotificationTimelineDTO::class.java)
                    if (res != null) {
                        firebaseResponseModel.onSuccess(res.asDomainModel())
                    }
                }

                override fun onFailure(error: OnFailureModel) {}
            })
    }
}