package br.com.savestudents.repository

import br.com.savestudents.dto.TimelineDTO
import br.com.savestudents.dto.asDomainModel
import br.com.savestudents.model.TimelineItem
import br.com.savestudents.model.repository.ITimelineRepository
import br.com.savestudents.service.external.FirebaseClient
import br.com.savestudents.service.external.model.FirebaseResponseModel
import br.com.savestudents.service.external.model.OnFailureModel
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