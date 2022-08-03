package com.example.savestudents.service

import com.example.savestudents.constants.FirestoreDbConstants
import com.example.savestudents.service.model.FirebaseClientModel
import com.example.savestudents.service.model.FirebaseResponseModel
import com.example.savestudents.service.model.OnFailureModel
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseClient : FirebaseClientModel {
    private val database = FirebaseFirestore.getInstance()
    override fun <T> getDocumentValue(
        collectionPath: String,
        firebaseResponseModel: FirebaseResponseModel<T>
    ) {
        database.collection(collectionPath).get().addOnSuccessListener { result ->
            if (result.documents.isEmpty()) {
                firebaseResponseModel.onFailure(
                    setErrorFailure(
                        FirestoreDbConstants.StatusCode.NOT_FOUND,
                        FirestoreDbConstants.MessageError.EMPTY_RESULT
                    )
                )
                return@addOnSuccessListener
            }

            val res = result.documents as T
            firebaseResponseModel.onSuccess(res)
        }.addOnFailureListener { error ->
            firebaseResponseModel.onFailure(
                setErrorFailure(
                    FirestoreDbConstants.StatusCode.BAD_REQUEST,
                    error.message.toString()
                )
            )
        }
    }

    override fun <T> getSpecificDocument(
        collectionPath: String,
        documentPath: String,
        firebaseResponseModel: FirebaseResponseModel<T>
    ) {
        database.collection(collectionPath).document(documentPath).get().addOnSuccessListener { result ->
            if (result.data?.isEmpty() == true) {
                firebaseResponseModel.onFailure(
                    setErrorFailure(
                        FirestoreDbConstants.StatusCode.NOT_FOUND,
                        FirestoreDbConstants.MessageError.EMPTY_RESULT
                    )
                )
                return@addOnSuccessListener
            }

            val res = result.data as T
            firebaseResponseModel.onSuccess(res)
        }.addOnFailureListener { error ->
            firebaseResponseModel.onFailure(
                setErrorFailure(
                    FirestoreDbConstants.StatusCode.BAD_REQUEST,
                    error.message.toString()
                )
            )
        }
    }

    override fun setValue(path: String): String {
        TODO("Not yet implemented")
    }

    override fun putValue(path: String): String {
        TODO("Not yet implemented")
    }

    override fun deleteValue(path: String): String {
        TODO("Not yet implemented")
    }

    private fun setErrorFailure(code: Int, message: String): OnFailureModel {
        return OnFailureModel(code, message)
    }
}