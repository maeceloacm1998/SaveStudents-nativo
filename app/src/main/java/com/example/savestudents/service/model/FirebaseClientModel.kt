package com.example.savestudents.service.model

interface FirebaseClientModel {
    fun <T> getDocumentValue(
        collectionPath: String,
        firebaseResponseModel: FirebaseResponseModel<T>
    )

    fun <T> getSpecificDocument(
        collectionPath: String,
        documentPath: String,
        firebaseResponseModel: FirebaseResponseModel<T>
    )

    fun setSpecificDocument(
        collectionPath: String,
        documentPath: String = "",
        data: Any
    )

    fun createDocument(collectionPath: String): String

    fun putDocument(collectionPath: String, documentPath: String, data: Any)

    fun deleteDocument(collectionPath: String, documentPath: String)
}