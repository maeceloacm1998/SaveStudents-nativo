package br.com.savestudents.repository

import br.com.savestudents.dto.FilterOptionDTO
import br.com.savestudents.model.repository.IFilterOptionsRepository
import br.com.savestudents.service.external.FirebaseClient
import br.com.savestudents.service.external.model.FirebaseResponseModel
import br.com.savestudents.service.external.model.OnFailureModel
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

            override fun onFailure(error: OnFailureModel) {
                firebaseResponseModel.onFailure(error)
            }
        })
    }
}