package com.example.savestudents.model.repository

import com.example.savestudents.dto.SubjectListDto
import com.example.savestudents.service.model.FirebaseResponseModel

interface IHomeRepository {
    fun getSubjectList(firebaseResponseModel: FirebaseResponseModel<List<SubjectListDto>>)
}