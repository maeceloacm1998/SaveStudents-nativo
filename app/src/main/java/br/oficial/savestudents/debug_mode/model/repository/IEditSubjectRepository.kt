package br.oficial.savestudents.debug_mode.model.repository

import br.oficial.savestudents.model.SubjectList
import br.oficial.savestudents.service.external.model.FirebaseResponseModel

interface IEditSubjectRepository {
    fun getSubjectPerId(id: String, firebaseResponseModel: FirebaseResponseModel<SubjectList>)
    fun updateSubjectItem(id: String, data: SubjectList, firebaseResponseModel: FirebaseResponseModel<Boolean>)
}