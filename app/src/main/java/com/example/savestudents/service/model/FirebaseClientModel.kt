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

    fun setValue(path: String): String

    fun putValue(path: String): String

    fun deleteValue(path: String): String
}