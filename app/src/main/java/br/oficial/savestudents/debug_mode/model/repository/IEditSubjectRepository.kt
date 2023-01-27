package br.oficial.savestudents.debug_mode.model.repository

import com.example.data_transfer.model.SubjectList
import com.example.data_transfer.model.repository.FirebaseResponseModel

interface IEditSubjectRepository {
    fun getSubjectPerId(id: String, firebaseResponseModel: FirebaseResponseModel<SubjectList>)
    fun updateSubjectItem(id: String, data: SubjectList, firebaseResponseModel: FirebaseResponseModel<Boolean>)
}