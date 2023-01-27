package br.oficial.savestudents.debug_mode.model.repository

import com.example.data_transfer.model.TimelineType
import com.example.data_transfer.model.repository.FirebaseResponseModel

interface ICreateTimelineRepository {
    fun getTimelineTypes(firebaseResponseModel: FirebaseResponseModel<List<TimelineType>>)
    fun createDocument(collectionPath: String): String
    fun setDocumentData(collectionPath: String, documentPath: String, data: Any)
}