package com.example.savestudents.model.repository

import com.example.savestudents.model.InformationModel
import com.example.savestudents.service.model.FirebaseResponseModel

interface ITimelineRepository {
    fun getInformation(collectionPath: String, firebaseResponseModel: FirebaseResponseModel<List<InformationModel>>)
}