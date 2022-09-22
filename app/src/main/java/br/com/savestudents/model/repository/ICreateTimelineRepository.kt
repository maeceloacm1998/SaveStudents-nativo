package br.com.savestudents.model.repository

interface ICreateTimelineRepository {
    fun createDocument(collectionPath: String): String
    fun setDocumentData(collectionPath: String, documentPath: String, data: Any)
}