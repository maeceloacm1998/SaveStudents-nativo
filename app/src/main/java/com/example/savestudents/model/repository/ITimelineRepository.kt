package com.example.savestudents.model.repository

import com.example.savestudents.model.TimelineItem
import com.example.savestudents.service.external.model.FirebaseResponseModel

interface ITimelineRepository {
    fun getInformation(collectionPath: String, firebaseResponseModel: FirebaseResponseModel<List<TimelineItem>>)
}