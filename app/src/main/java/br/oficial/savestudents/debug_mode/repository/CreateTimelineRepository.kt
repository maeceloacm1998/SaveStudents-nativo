package br.oficial.savestudents.debug_mode.repository

import com.br.core.constants.FirestoreDbConstants
import br.oficial.savestudents.debug_mode.model.repository.ICreateTimelineRepository
import com.br.core.service.external.FirebaseClient
import com.example.data_transfer.dto.TimelineTypeDTO
import com.example.data_transfer.model.OnFailureModel
import com.example.data_transfer.model.TimelineType
import com.example.data_transfer.model.asDomainModel
import com.example.data_transfer.model.repository.FirebaseResponseModel
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