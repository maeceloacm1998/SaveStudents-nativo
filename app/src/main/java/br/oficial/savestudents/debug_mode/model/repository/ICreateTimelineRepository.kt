package br.oficial.savestudents.debug_mode.model.repository

import br.oficial.savestudents.model.TimelineType
import br.oficial.savestudents.service.external.model.FirebaseResponseModel

interface ICreateTimelineRepository {
    fun getTimelineTypes(firebaseResponseModel: FirebaseResponseModel<List<TimelineType>>)
    fun createDocument(collectionPath: String): String
    fun setDocumentData(collectionPath: String, documentPath: String, data: Any)
}