package br.oficial.savestudents.repository

import br.oficial.savestudents.constants.FirestoreDbConstants
import br.oficial.savestudents.dto.SubjectListDto
import br.oficial.savestudents.model.repository.IHomeRepository
import br.oficial.savestudents.service.external.FirebaseClient
import br.oficial.savestudents.service.external.model.FirebaseResponseModel
import br.oficial.savestudents.service.external.model.OnFailureModel
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