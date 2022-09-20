package com.example.savestudents.repository

import com.example.savestudents.model.repository.ICreateTimelineRepository
import com.example.savestudents.service.external.FirebaseClient

class CreateTimelineRepository : ICreateTimelineRepository {
    private val client = FirebaseClient()
    override fun createDocument(collectionPath: String): String {
        return client.createDocument(collectionPath)
    }

    override fun setDocumentData(collectionPath: String, documentPath: String, data: Any) {
        client.setSpecificDocument(collectionPath, documentPath, data)
    }
}