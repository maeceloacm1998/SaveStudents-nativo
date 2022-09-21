package com.example.savestudents.repository

import com.example.savestudents.dto.TimelineDTO
import com.example.savestudents.dto.asDomainModel
import com.example.savestudents.model.TimelineItem
import com.example.savestudents.model.repository.ITimelineRepository
import com.example.savestudents.service.external.FirebaseClient
import com.example.savestudents.service.external.model.FirebaseResponseModel
import com.example.savestudents.service.external.model.OnFailureModel
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