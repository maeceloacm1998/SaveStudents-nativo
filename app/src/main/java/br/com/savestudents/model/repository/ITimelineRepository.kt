package br.com.savestudents.model.repository

import br.com.savestudents.model.TimelineItem
import br.com.savestudents.service.external.model.FirebaseResponseModel

interface ITimelineRepository {
    fun getInformation(collectionPath: String, firebaseResponseModel: FirebaseResponseModel<List<TimelineItem>>)
}