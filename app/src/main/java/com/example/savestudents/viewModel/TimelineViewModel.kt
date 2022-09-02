package com.example.savestudents.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.savestudents.model.InformationModel
import com.example.savestudents.model.repository.ITimelineRepository
import com.example.savestudents.model.viewModel.ITimelineViewModel
import com.example.savestudents.repository.TimelineRepository
import com.example.savestudents.service.model.FirebaseResponseModel
import com.example.savestudents.service.model.OnFailureModel

class TimelineViewModel : ViewModel(), ITimelineViewModel {
    private val repository: ITimelineRepository = TimelineRepository()

    private val mInformationData = MutableLiveData<List<InformationModel>>()
    var informationData: LiveData<List<InformationModel>> = mInformationData

    override fun getInformation(collectionPath: String) {
        repository.getInformation(
            collectionPath,
            object : FirebaseResponseModel<List<InformationModel>> {
                override fun onSuccess(model: List<InformationModel>) {
                    mInformationData.value = model
                }

                override fun onFailure(error: OnFailureModel) {
                    TODO("Not yet implemented")
                }
            })
    }
}