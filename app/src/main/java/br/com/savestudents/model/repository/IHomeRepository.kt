package br.com.savestudents.model.repository

import br.com.savestudents.dto.SubjectListDto
import br.com.savestudents.service.external.model.FirebaseResponseModel

interface IHomeRepository {
    fun getSubjectList(firebaseResponseModel: FirebaseResponseModel<List<SubjectListDto>>)
    fun getFilterOptions(collectionPath: String, field: String, values: MutableList<String>, firebaseResponseModel: FirebaseResponseModel<List<SubjectListDto>>)
}