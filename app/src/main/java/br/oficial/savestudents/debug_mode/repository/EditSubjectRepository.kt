package br.oficial.savestudents.debug_mode.repository

import com.br.core.constants.FirestoreDbConstants
import br.oficial.savestudents.debug_mode.model.repository.IEditSubjectRepository
import com.br.core.service.external.FirebaseClient
import com.example.data_transfer.dto.SubjectListDto
import com.example.data_transfer.model.OnFailureModel
import com.example.data_transfer.model.SubjectList
import com.example.data_transfer.model.asModel
import com.example.data_transfer.model.repository.FirebaseResponseModel
import com.google.firebase.firestore.DocumentSnapshot

class EditSubjectRepository() : IEditSubjectRepository {
    private val client = FirebaseClient()

    override fun getSubjectPerId(
        id: String,
        firebaseResponseModel: FirebaseResponseModel<SubjectList>
    ) {
        client.getSpecificDocument(
            FirestoreDbConstants.Collections.SUBJECTS_LIST,
            id,
            object : FirebaseResponseModel<DocumentSnapshot> {
                override fun onSuccess(model: DocumentSnapshot) {
                    val res = model.toObject(SubjectListDto::class.java)
                    res?.let { firebaseResponseModel.onSuccess(it.asModel()) }
                }


                override fun onFailure(error: OnFailureModel) {
                    firebaseResponseModel.onFailure(error)
                }
            })
    }

    override fun updateSubjectItem(
        id: String,
        data: SubjectList,
        firebaseResponseModel: FirebaseResponseModel<Boolean>
    ) {
        client.setSpecificDocument(FirestoreDbConstants.Collections.SUBJECTS_LIST, id, data, object :
            FirebaseResponseModel<Boolean> {
            override fun onSuccess(model: Boolean) {
                firebaseResponseModel.onSuccess(model)
            }

            override fun onFailure(error: OnFailureModel) {
                firebaseResponseModel.onFailure(error)
            }
        })

        client.updateDocument(FirestoreDbConstants.Collections.TIMELINE_LIST,id, "subjectsInformation", data)
    }
}