package br.oficial.savestudents.model.repository

import br.oficial.savestudents.dto.SubjectListDto
import br.oficial.savestudents.service.external.model.FirebaseResponseModel

interface IHomeRepository {
    fun getSubjectList(firebaseResponseModel: FirebaseResponseModel<List<SubjectListDto>>)
    fun getFilterOptions(collectionPath: String, field: String, values: MutableList<String>, firebaseResponseModel: FirebaseResponseModel<List<SubjectListDto>>)
}