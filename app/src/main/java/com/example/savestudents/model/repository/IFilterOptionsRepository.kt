package com.example.savestudents.model.repository

import com.example.savestudents.dto.FilterOptionDTO
import com.example.savestudents.service.model.FirebaseResponseModel

interface IFilterOptionsRepository {
    fun getFilterOptions(
        collectionPath: String,
        orderByName: String,
        firebaseResponseModel: FirebaseResponseModel<List<FilterOptionDTO>>
    )
}