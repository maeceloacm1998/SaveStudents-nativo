package com.savestudents.core.firebase

interface FirebaseResponseModel<T> {
    fun onSuccess(model: T)
    fun onFailure(error: OnFailureModel)
}

data class OnFailureModel(
    val code: Int,
    val message: String
)