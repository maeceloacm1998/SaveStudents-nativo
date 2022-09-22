package br.com.savestudents.model.repository

import br.com.savestudents.dto.FilterOptionDTO
import br.com.savestudents.service.external.model.FirebaseResponseModel

interface IFilterOptionsRepository {
    fun getFilterOptions(
        collectionPath: String,
        orderByName: String,
        firebaseResponseModel: FirebaseResponseModel<List<FilterOptionDTO>>
    )
}