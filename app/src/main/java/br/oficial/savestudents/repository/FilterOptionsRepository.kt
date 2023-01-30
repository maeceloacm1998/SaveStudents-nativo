package br.oficial.savestudents.repository

import com.br.core.service.external.FirebaseClient
import com.example.data_transfer.dto.FilterOptionDTO
import com.example.data_transfer.model.repository.FirebaseResponseModel
import com.example.data_transfer.model.repository.IFilterOptionsRepository
import com.google.firebase.firestore.QueryDocumentSnapshot

class FilterOptionsRepository : IFilterOptionsRepository {
    private val client = FirebaseClient()

    override fun getFilterOptions(
        collectionPath: String,
        orderByName:String,
        firebaseResponseModel: FirebaseResponseModel<List<FilterOptionDTO>>
    ) {
        client.getDocumentWithOrderByValue(collectionPath, orderByName, object :
            FirebaseResponseModel<List<QueryDocumentSnapshot>> {
            override fun onSuccess(model: List<QueryDocumentSnapshot>) {
                val res = model.map { it.toObject(FilterOptionDTO::class.java) }
                firebaseResponseModel.onSuccess(res)
            }

            override fun onFailure(error: com.example.data_transfer.model.OnFailureModel) {
                firebaseResponseModel.onFailure(error)
            }
        })
    }
}