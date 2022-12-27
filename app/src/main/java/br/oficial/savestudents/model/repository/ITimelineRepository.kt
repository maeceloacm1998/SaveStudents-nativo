package br.oficial.savestudents.model.repository

import br.oficial.savestudents.model.TimelineItem
import br.oficial.savestudents.service.external.model.FirebaseResponseModel

interface ITimelineRepository {
    fun getInformation(collectionPath: String, firebaseResponseModel: FirebaseResponseModel<List<TimelineItem>>)
}