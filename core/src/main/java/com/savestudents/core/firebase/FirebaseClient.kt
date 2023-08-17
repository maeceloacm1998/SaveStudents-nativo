package com.savestudents.core.firebase

import com.google.firebase.firestore.DocumentSnapshot

interface FirebaseClient {
    fun <T> getDocumentValue(
        collectionPath: String,
        firebaseResponseModel: FirebaseResponseModel<T>
    )

    fun <T> getDocumentWithOrderByValue(
        collectionPath: String,
        orderByName: String?,
        firebaseResponseModel: FirebaseResponseModel<T>
    )

    fun <T> getFilterDocuments(
        collectionPath: String,
        field: String,
        values: MutableList<String>,
        firebaseResponseModel: FirebaseResponseModel<T>
    )

    fun getSpecificDocument(
        collectionPath: String,
        documentPath: String,
        firebaseResponseModel: FirebaseResponseModel<DocumentSnapshot>,
    )

    fun setSpecificDocument(
        collectionPath: String,
        documentPath: String,
        data: Any,
        firebaseResponseModel: FirebaseResponseModel<Boolean>
    )

    fun createDocument(collectionPath: String): String

    fun deleteDocument(collectionPath: String, documentPath: String)

    fun updateDocument(collectionPath: String, documentPath: String, field: String, value: Any)
}