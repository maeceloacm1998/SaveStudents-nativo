package com.example.data_transfer.model.repository

import com.example.data_transfer.model.TimelineItem


interface ITimelineRepository {
    fun getInformation(collectionPath: String, firebaseResponseModel: FirebaseResponseModel<List<TimelineItem>>)
}