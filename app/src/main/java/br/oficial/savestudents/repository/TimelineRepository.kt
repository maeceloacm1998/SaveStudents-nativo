package br.oficial.savestudents.repository

import com.example.data_transfer.dto.asDomainModel
import com.br.core.service.external.FirebaseClient
import com.example.data_transfer.dto.TimelineDTO
import com.example.data_transfer.model.OnFailureModel
import com.example.data_transfer.model.TimelineItem
import com.example.data_transfer.model.repository.FirebaseResponseModel
import com.example.data_transfer.model.repository.ITimelineRepository
import com.google.firebase.firestore.QueryDocumentSnapshot

class TimelineRepository : ITimelineRepository {
    private val client = FirebaseClient()

    override fun getInformation(
        collectionPath: String,
        firebaseResponseModel: FirebaseResponseModel<List<TimelineItem>>
    ) {
        client.getDocumentValue(
            collectionPath,
            object : FirebaseResponseModel<List<QueryDocumentSnapshot>> {
                override fun onSuccess(model: List<QueryDocumentSnapshot>) {
                    val res = model.map { it.toObject(TimelineDTO::class.java) }
                    firebaseResponseModel.onSuccess(res.asDomainModel())
                }

                override fun onFailure(error: OnFailureModel) {
                    firebaseResponseModel.onFailure(error)
                }
            })
    }
}