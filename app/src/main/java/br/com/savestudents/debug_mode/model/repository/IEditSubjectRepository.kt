package br.com.savestudents.debug_mode.model.repository

import br.com.savestudents.model.SubjectList
import br.com.savestudents.service.external.model.FirebaseResponseModel

interface IEditSubjectRepository {
    fun getSubjectPerId(id: String, firebaseResponseModel: FirebaseResponseModel<SubjectList>)
    fun updateSubjectItem(id: String, data: SubjectList, firebaseResponseModel: FirebaseResponseModel<Boolean>)
}