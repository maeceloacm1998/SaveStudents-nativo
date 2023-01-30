package com.example.data_transfer.model.repository

import com.example.data_transfer.dto.FilterOptionDTO

interface IFilterOptionsRepository {
    fun getFilterOptions(
        collectionPath: String,
        orderByName: String,
        firebaseResponseModel: FirebaseResponseModel<List<FilterOptionDTO>>
    )
}