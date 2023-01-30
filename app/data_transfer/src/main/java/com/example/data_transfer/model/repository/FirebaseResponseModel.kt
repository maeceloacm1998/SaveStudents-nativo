package com.example.data_transfer.model.repository

import com.example.data_transfer.model.OnFailureModel

interface FirebaseResponseModel<T> {
    fun onSuccess(model: T)
    fun onFailure(error: OnFailureModel)
}