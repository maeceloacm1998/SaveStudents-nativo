package br.oficial.savestudents.repository

import br.oficial.savestudents.constants.FirestoreDbConstants
import br.oficial.savestudents.dto.NotificationTimelineDTO
import br.oficial.savestudents.dto.asDomainModel
import br.oficial.savestudents.model.NotificationTimeline
import br.oficial.savestudents.model.repository.ITimelineSettingsRepository
import br.oficial.savestudents.service.external.FirebaseClient
import br.oficial.savestudents.service.external.model.FirebaseResponseModel
import br.oficial.savestudents.service.external.model.OnFailureModel
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