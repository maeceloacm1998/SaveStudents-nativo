package com.example.savestudents.repository

import com.example.savestudents.constants.FirestoreDbConstants
import com.example.savestudents.dto.SubjectListDto
import com.example.savestudents.model.repository.IHomeRepository
import com.example.savestudents.service.FirebaseClient
import com.example.savestudents.service.model.FirebaseResponseModel
import com.example.savestudents.service.model.OnFailureModel
import com.google.firebase.firestore.QueryDocumentSnapshot

class HomeRepository : IHomeRepository {
    private val client = FirebaseClient()

    override fun getSubjectList(
        firebaseResponseModel: FirebaseResponseModel<List<SubjectListDto>>
    ) {
        client.getDocumentValue(FirestoreDbConstants.Collections.SUBJECTS_LIST, "", object : FirebaseResponseModel<List<QueryDocumentSnapshot>> {
                override fun onSuccess(model: List<QueryDocumentSnapshot>) {
                    val res = model.map { it.toObject(SubjectListDto::class.java) }
                    firebaseResponseModel.onSuccess(res)
                }

                override fun onFailure(error: OnFailureModel) {
                    firebaseResponseModel.onFailure(error)
                }
            })
    }
}