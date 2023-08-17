package com.savestudents.core.firebase

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FirebaseClientImpl(private val database: FirebaseFirestore): FirebaseClient {
    override fun <T> getDocumentValue(
        collectionPath: String,
        firebaseResponseModel: FirebaseResponseModel<T>
    ) {
        database.collection(collectionPath).get().addOnSuccessListener { result ->
            if (result.documents.isEmpty()) {
                firebaseResponseModel.onFailure(
                    setErrorFailure(
                        FirebaseConstants.StatusCode.NOT_FOUND,
                        FirebaseConstants.MessageError.EMPTY_RESULT
                    )
                )
                handleLog(
                    FirebaseConstants.MethodsFirebaseClient.GET_DOCUMENT_VALUE,
                    collectionPath,
                    FirebaseConstants.StatusCode.NOT_FOUND.toString(),
                    FirebaseConstants.MessageError.EMPTY_RESULT
                )
                return@addOnSuccessListener
            }

            val res = result.documents as T
            handleLog(
                FirebaseConstants.MethodsFirebaseClient.GET_DOCUMENT_VALUE,
                collectionPath,
                FirebaseConstants.StatusCode.SUCCESS.toString(),
                res.toString()
            )

            firebaseResponseModel.onSuccess(res)
        }
    }

    override fun <T> getDocumentWithOrderByValue(
        collectionPath: String,
        orderByName: String?,
        firebaseResponseModel: FirebaseResponseModel<T>
    ) {
        database.collection(collectionPath).orderBy(orderByName ?: "", Query.Direction.ASCENDING)
            .get().addOnSuccessListener { result ->
                if (result.documents.isEmpty()) {
                    firebaseResponseModel.onFailure(
                        setErrorFailure(
                            FirebaseConstants.StatusCode.NOT_FOUND,
                            FirebaseConstants.MessageError.EMPTY_RESULT
                        )
                    )
                    handleLog(
                        FirebaseConstants.MethodsFirebaseClient.GET_DOCUMENT_VALUE,
                        collectionPath,
                        FirebaseConstants.StatusCode.NOT_FOUND.toString(),
                        FirebaseConstants.MessageError.EMPTY_RESULT
                    )
                    return@addOnSuccessListener
                }

                val res = result.documents as T
                handleLog(
                    FirebaseConstants.MethodsFirebaseClient.GET_DOCUMENT_VALUE,
                    collectionPath,
                    FirebaseConstants.StatusCode.SUCCESS.toString(),
                    res.toString()
                )

                firebaseResponseModel.onSuccess(res)
            }
    }

    override fun <T> getFilterDocuments(
        collectionPath: String,
        field: String,
        values: MutableList<String>,
        firebaseResponseModel: FirebaseResponseModel<T>
    ) {
        database.collection(collectionPath).whereIn(field, values).get()
            .addOnSuccessListener { result ->
                if (result.documents.isEmpty()) {
                    firebaseResponseModel.onFailure(
                        setErrorFailure(
                            FirebaseConstants.StatusCode.NOT_FOUND,
                            FirebaseConstants.MessageError.EMPTY_RESULT
                        )
                    )
                    handleLog(
                        FirebaseConstants.MethodsFirebaseClient.GET_FILTER_DOCUMENT,
                        collectionPath,
                        FirebaseConstants.StatusCode.NOT_FOUND.toString(),
                        FirebaseConstants.MessageError.EMPTY_RESULT
                    )
                    return@addOnSuccessListener
                }

                val res = result.documents as T
                handleLog(
                    FirebaseConstants.MethodsFirebaseClient.GET_DOCUMENT_VALUE,
                    collectionPath,
                    FirebaseConstants.StatusCode.SUCCESS.toString(),
                    res.toString()
                )

                firebaseResponseModel.onSuccess(res)
            }
    }

    override fun getSpecificDocument(
        collectionPath: String,
        documentPath: String,
        firebaseResponseModel: FirebaseResponseModel<DocumentSnapshot>
    ) {
        database.collection(collectionPath).document(documentPath).get()
            .addOnSuccessListener { result ->
                if (result.data?.isEmpty() == true) {
                    firebaseResponseModel.onFailure(
                        setErrorFailure(
                            FirebaseConstants.StatusCode.NOT_FOUND,
                            FirebaseConstants.MessageError.EMPTY_RESULT
                        )
                    )
                    handleLog(
                        FirebaseConstants.MethodsFirebaseClient.GET_SPECIFIC_DOCUMENT,
                        collectionPath,
                        FirebaseConstants.StatusCode.NOT_FOUND.toString(),
                        FirebaseConstants.MessageError.EMPTY_RESULT
                    )
                    return@addOnSuccessListener
                }

                val res = result as DocumentSnapshot
                handleLog(
                    FirebaseConstants.MethodsFirebaseClient.GET_SPECIFIC_DOCUMENT,
                    collectionPath,
                    FirebaseConstants.StatusCode.SUCCESS.toString(),
                    res.toString()
                )
                firebaseResponseModel.onSuccess(res)
            }
    }

    override fun setSpecificDocument(
        collectionPath: String,
        documentPath: String,
        data: Any,
        firebaseResponseModel: FirebaseResponseModel<Boolean>,
    ) {
        database.collection(collectionPath).document(documentPath).set(data).addOnSuccessListener {
            handleLog(
                FirebaseConstants.MethodsFirebaseClient.SET_SPECIFIC_DOCUMENT,
                collectionPath,
                FirebaseConstants.StatusCode.SUCCESS.toString(),
                true.toString()
            )
            firebaseResponseModel.onSuccess(true)
        }.addOnFailureListener {
            firebaseResponseModel.onFailure(
                setErrorFailure(
                    FirebaseConstants.StatusCode.NOT_FOUND,
                    FirebaseConstants.MessageError.UPDATE_ERROR
                )
            )
            handleLog(
                FirebaseConstants.MethodsFirebaseClient.SET_SPECIFIC_DOCUMENT,
                collectionPath,
                FirebaseConstants.StatusCode.NOT_FOUND.toString(),
                FirebaseConstants.MessageError.UPDATE_ERROR
            )
        }
    }

    override fun createDocument(collectionPath: String): String {
        val id = database.collection(collectionPath).document().id
        database.collection(collectionPath).document(id).set({})

        return id
    }

    override fun deleteDocument(collectionPath: String, documentPath: String) {
        database.collection(collectionPath).document(documentPath).delete()
    }

    override fun updateDocument(
        collectionPath: String,
        documentPath: String,
        field: String,
        value: Any
    ) {
        database.collection(collectionPath).document(documentPath).update(field,value)
    }

    private fun setErrorFailure(code: Int, message: String): OnFailureModel {
        return OnFailureModel(code, message)
    }

    private fun handleLog(typeRequisition: String, collectionPath: String, statusCode: String, data: String) {
        Log.d("=================> $typeRequisition",  "PATH:$collectionPath -- STATUS_CODE:$statusCode -- DATA:$data")
    }
}