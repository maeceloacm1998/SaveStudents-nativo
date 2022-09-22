package br.com.savestudents.service.external.model

interface FirebaseResponseModel<T> {
    fun onSuccess(model: T)

    fun onFailure(error: OnFailureModel)
}