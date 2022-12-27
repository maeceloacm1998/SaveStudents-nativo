package br.oficial.savestudents.debug_mode.repository

import br.oficial.savestudents.constants.FirestoreDbConstants
import br.oficial.savestudents.debug_mode.model.repository.IEditSubjectRepository
import br.oficial.savestudents.dto.SubjectListDto
import br.oficial.savestudents.dto.asModel
import br.oficial.savestudents.model.SubjectList
import br.oficial.savestudents.service.external.FirebaseClient
import br.oficial.savestudents.service.external.model.FirebaseResponseModel
import br.oficial.savestudents.service.external.model.OnFailureModel
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
        client.setSpecificDocument(FirestoreDbConstants.Collections.SUBJECTS_LIST, id, data, object : FirebaseResponseModel<Boolean> {
            override fun onSuccess(model: Boolean) {
                firebaseResponseModel.onSuccess(model)
            }

            override fun onFailure(error: OnFailureModel) {
                firebaseResponseModel.onFailure(error)
            }
        })
    }
}