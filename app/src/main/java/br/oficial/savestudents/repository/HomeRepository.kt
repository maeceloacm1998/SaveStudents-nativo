package br.oficial.savestudents.repository

import com.br.core.constants.FirestoreDbConstants
import com.br.core.service.external.FirebaseClient
import com.example.data_transfer.dto.SubjectListDto
import com.example.data_transfer.model.OnFailureModel
import com.example.data_transfer.model.repository.FirebaseResponseModel
import com.example.data_transfer.model.repository.IHomeRepository
import com.google.firebase.firestore.QueryDocumentSnapshot

class HomeRepository : IHomeRepository {
    private val client = FirebaseClient()

    override fun getSubjectList(firebaseResponseModel: FirebaseResponseModel<List<SubjectListDto>>) {
        client.getDocumentValue(
            FirestoreDbConstants.Collections.SUBJECTS_LIST,
            object :
                FirebaseResponseModel<List<QueryDocumentSnapshot>> {
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
            object :
                FirebaseResponseModel<List<QueryDocumentSnapshot>> {
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