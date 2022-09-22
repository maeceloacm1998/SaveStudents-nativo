package br.com.savestudents.repository

import br.com.savestudents.constants.FirestoreDbConstants
import br.com.savestudents.dto.SubjectListDto
import br.com.savestudents.model.repository.IHomeRepository
import br.com.savestudents.service.external.FirebaseClient
import br.com.savestudents.service.external.model.FirebaseResponseModel
import br.com.savestudents.service.external.model.OnFailureModel
import com.google.firebase.firestore.QueryDocumentSnapshot

class HomeRepository : IHomeRepository {
    private val client = FirebaseClient()

    override fun getSubjectList(
        firebaseResponseModel: FirebaseResponseModel<List<SubjectListDto>>
    ) {
        client.getDocumentValue(
            FirestoreDbConstants.Collections.SUBJECTS_LIST,
            object : FirebaseResponseModel<List<QueryDocumentSnapshot>> {
                override fun onSuccess(model: List<QueryDocumentSnapshot>) {
                    val res = model.map { it.toObject(SubjectListDto::class.java) }
                    firebaseResponseModel.onSuccess(res)
                }

                override fun onFailure(error: OnFailureModel) {
                    firebaseResponseModel.onFailure(error)
                }
            })
    }

    override fun getFilterOptions(
        collectionPath: String,
        field: String,
        values: MutableList<String>,
        firebaseResponseModel: FirebaseResponseModel<List<SubjectListDto>>
    ) {
        client.getFilterDocuments(collectionPath, field, values,
            object : FirebaseResponseModel<List<QueryDocumentSnapshot>> {
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