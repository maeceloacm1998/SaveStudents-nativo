package br.com.savestudents.repository

import br.com.savestudents.model.repository.ICreateTimelineRepository
import br.com.savestudents.service.external.FirebaseClient
import br.com.savestudents.service.external.model.FirebaseResponseModel
import br.com.savestudents.service.external.model.OnFailureModel

class CreateTimelineRepository : ICreateTimelineRepository {
    private val client = FirebaseClient()
    override fun createDocument(collectionPath: String): String {
        return client.createDocument(collectionPath)
    }

    override fun setDocumentData(collectionPath: String, documentPath: String, data: Any) {
        client.setSpecificDocument(collectionPath, documentPath, data, object :FirebaseResponseModel<Boolean> {
            override fun onSuccess(model: Boolean) {}

            override fun onFailure(error: OnFailureModel) {}
        })
    }
}