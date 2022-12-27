package br.oficial.savestudents.debug_mode.repository

import br.oficial.savestudents.constants.FirestoreDbConstants
import br.oficial.savestudents.debug_mode.model.repository.IEditTimelineRepository
import br.oficial.savestudents.dto.TimelineDTO
import br.oficial.savestudents.model.TimelineItem
import br.oficial.savestudents.service.external.FirebaseClient
import br.oficial.savestudents.service.external.model.FirebaseResponseModel
import br.oficial.savestudents.service.external.model.OnFailureModel
import com.google.firebase.firestore.DocumentSnapshot

class EditTimelineRepository : IEditTimelineRepository {
    private val client = FirebaseClient()

    override fun getSubjectPerId(
        id: String,
        firebaseResponseModel: FirebaseResponseModel<TimelineDTO>
    ) {
        client.getSpecificDocument(
            FirestoreDbConstants.Collections.TIMELINE_LIST,
            id,
            object : FirebaseResponseModel<DocumentSnapshot> {
                override fun onSuccess(model: DocumentSnapshot) {
                    val modelToObject = model.toObject(TimelineDTO::class.java)
                    modelToObject?.let { firebaseResponseModel.onSuccess(it) }
                }

                override fun onFailure(error: OnFailureModel) {
                    TODO("Not yet implemented")
                }
            })
    }

    override fun updateTimelineItem(
        timelineItem: TimelineItem,
        firebaseResponseModel: FirebaseResponseModel<Boolean>
    ) {
        client.setSpecificDocument(
            FirestoreDbConstants.Collections.TIMELINE_LIST,
            timelineItem.subjectsInformation!!.id,
            timelineItem,
            object : FirebaseResponseModel<Boolean> {
                override fun onSuccess(model: Boolean) {
                    firebaseResponseModel.onSuccess(model)
                }

                override fun onFailure(error: OnFailureModel) {
                    TODO("Not yet implemented")
                }
            })
    }
}