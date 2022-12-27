package br.oficial.savestudents.model.repository

import br.oficial.savestudents.dto.FilterOptionDTO
import br.oficial.savestudents.service.external.model.FirebaseResponseModel

interface IFilterOptionsRepository {
    fun getFilterOptions(
        collectionPath: String,
        orderByName: String,
        firebaseResponseModel: FirebaseResponseModel<List<FilterOptionDTO>>
    )
}