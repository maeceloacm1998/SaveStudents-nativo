package com.example.data_transfer.model.repository

import com.example.data_transfer.dto.SubjectListDto


interface IHomeRepository {
    fun getSubjectList(firebaseResponseModel: FirebaseResponseModel<List<SubjectListDto>>)
    fun getFilterOptions(collectionPath: String, field: String, values: MutableList<String>, firebaseResponseModel: FirebaseResponseModel<List<SubjectListDto>>)
}