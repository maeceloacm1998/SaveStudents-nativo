package br.com.savestudents.service.external

import android.util.Log
import br.com.savestudents.constants.FirestoreDbConstants
import br.com.savestudents.service.external.model.FirebaseClientModel
import br.com.savestudents.service.external.model.FirebaseResponseModel
import br.com.savestudents.service.external.model.OnFailureModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

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
                handleLog(
                    FirestoreDbConstants.MethodsFirebaseClient.GET_DOCUMENT_VALUE,
                    collectionPath,
                    FirestoreDbConstants.StatusCode.NOT_FOUND.toString(),
                    FirestoreDbConstants.MessageError.EMPTY_RESULT
                )
                return@addOnSuccessListener
            }

            val res = result.documents as T
            handleLog(
                FirestoreDbConstants.MethodsFirebaseClient.GET_DOCUMENT_VALUE,
                collectionPath,
                FirestoreDbConstants.StatusCode.SUCCESS.toString(),
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
                            FirestoreDbConstants.StatusCode.NOT_FOUND,
                            FirestoreDbConstants.MessageError.EMPTY_RESULT
                        )
                    )
                    handleLog(
                        FirestoreDbConstants.MethodsFirebaseClient.GET_DOCUMENT_VALUE,
                        collectionPath,
                        FirestoreDbConstants.StatusCode.NOT_FOUND.toString(),
                        FirestoreDbConstants.MessageError.EMPTY_RESULT
                    )
                    return@addOnSuccessListener
                }

                val res = result.documents as T
                handleLog(
                    FirestoreDbConstants.MethodsFirebaseClient.GET_DOCUMENT_VALUE,
                    collectionPath,
                    FirestoreDbConstants.StatusCode.SUCCESS.toString(),
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
                            FirestoreDbConstants.StatusCode.NOT_FOUND,
                            FirestoreDbConstants.MessageError.EMPTY_RESULT
                        )
                    )
                    handleLog(
                        FirestoreDbConstants.MethodsFirebaseClient.GET_FILTER_DOCUMENT,
                        collectionPath,
                        FirestoreDbConstants.StatusCode.NOT_FOUND.toString(),
                        FirestoreDbConstants.MessageError.EMPTY_RESULT
                    )
                    return@addOnSuccessListener
                }

                val res = result.documents as T
                handleLog(
                    FirestoreDbConstants.MethodsFirebaseClient.GET_DOCUMENT_VALUE,
                    collectionPath,
                    FirestoreDbConstants.StatusCode.SUCCESS.toString(),
                    res.toString()
                )

                firebaseResponseModel.onSuccess(res)
            }
    }

    override fun <T> getSpecificDocument(
        collectionPath: String,
        documentPath: String,
        firebaseResponseModel: FirebaseResponseModel<T>
    ) {
        database.collection(collectionPath).document(documentPath).get()
            .addOnSuccessListener { result ->
                if (result.data?.isEmpty() == true) {
                    firebaseResponseModel.onFailure(
                        setErrorFailure(
                            FirestoreDbConstants.StatusCode.NOT_FOUND,
                            FirestoreDbConstants.MessageError.EMPTY_RESULT
                        )
                    )
                    handleLog(
                        FirestoreDbConstants.MethodsFirebaseClient.GET_SPECIFIC_DOCUMENT,
                        collectionPath,
                        FirestoreDbConstants.StatusCode.NOT_FOUND.toString(),
                        FirestoreDbConstants.MessageError.EMPTY_RESULT
                    )
                    return@addOnSuccessListener
                }

                val res = result.data as T
                handleLog(
                    FirestoreDbConstants.MethodsFirebaseClient.GET_SPECIFIC_DOCUMENT,
                    collectionPath,
                    FirestoreDbConstants.StatusCode.SUCCESS.toString(),
                    res.toString()
                )
                firebaseResponseModel.onSuccess(res)
            }
    }

    override fun setSpecificDocument(collectionPath: String, documentPath: String, data: Any) {
        database.collection(collectionPath).document(documentPath).set(data)
    }

    override fun createDocument(collectionPath: String): String {
        val id = database.collection(collectionPath).document().id
        database.collection(collectionPath).document(id).set({})

        return id
    }

    override fun putDocument(collectionPath: String, documentPath: String, data: Any) {
        database.collection(collectionPath).document(documentPath).set(data)
    }

    override fun deleteDocument(collectionPath: String, documentPath: String) {
        database.collection(collectionPath).document(documentPath).delete()
    }

    private fun setErrorFailure(code: Int, message: String): OnFailureModel {
        return OnFailureModel(code, message)
    }

    private fun handleLog(typeRequisition: String, collectionPath: String, statusCode: String, data: String) {
        Log.d("=================> $typeRequisition",  "PATH:$collectionPath -- STATUS_CODE:$statusCode -- DATA:$data")
    }
}