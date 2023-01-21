package br.oficial.savestudents.debug_mode.repository

import br.oficial.savestudents.constants.FirestoreDbConstants
import br.oficial.savestudents.debug_mode.model.repository.ICreateTimelineRepository
import br.oficial.savestudents.dto.TimelineTypeDTO
import br.oficial.savestudents.model.TimelineType
import br.oficial.savestudents.model.asDomainModel
import br.oficial.savestudents.service.external.FirebaseClient
import br.oficial.savestudents.service.external.model.FirebaseResponseModel
import br.oficial.savestudents.service.external.model.OnFailureModel
import com.google.firebase.firestore.QueryDocumentSnapshot

class CreateTimelineRepository : ICreateTimelineRepository {
    private val client = FirebaseClient()
    override fun getTimelineTypes(firebaseResponseModel: FirebaseResponseModel<List<TimelineType>>) {
        client.getDocumentValue(
            FirestoreDbConstants.Collections.TIMELINE_TYPES,
            object : FirebaseResponseModel<List<QueryDocumentSnapshot>> {
                override fun onSuccess(model: List<QueryDocumentSnapshot>) {
                    val res = model.map { it.toObject(TimelineTypeDTO::class.java).asDomainModel() }
                    firebaseResponseModel.onSuccess(res)
                }

                override fun onFailure(error: OnFailureModel) {
                    firebaseResponseModel.onFailure(error)
                }
            })
    }

    override fun createDocument(collectionPath: String): String {
        return client.createDocument(collectionPath)
    }

    override fun setDocumentData(collectionPath: String, documentPath: String, data: Any) {
        client.setSpecificDocument(
            collectionPath,
            documentPath,
            data,
            object : FirebaseResponseModel<Boolean> {
                override fun onSuccess(model: Boolean) {}

                override fun onFailure(error: OnFailureModel) {}
            })
    }
}