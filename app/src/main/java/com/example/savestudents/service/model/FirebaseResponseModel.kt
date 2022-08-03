package com.example.savestudents.service.model

interface FirebaseResponseModel<T> {
    fun onSuccess(model: T)

    fun onFailure(error: OnFailureModel)
}