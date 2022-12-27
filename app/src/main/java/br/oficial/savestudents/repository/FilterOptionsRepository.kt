package br.oficial.savestudents.repository

import br.oficial.savestudents.dto.FilterOptionDTO
import br.oficial.savestudents.model.repository.IFilterOptionsRepository
import br.oficial.savestudents.service.external.FirebaseClient
import br.oficial.savestudents.service.external.model.FirebaseResponseModel
import br.oficial.savestudents.service.external.model.OnFailureModel
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