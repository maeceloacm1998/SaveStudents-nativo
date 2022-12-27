package br.oficial.savestudents.service.external.model

interface FirebaseResponseModel<T> {
    fun onSuccess(model: T)

    fun onFailure(error: OnFailureModel)
}