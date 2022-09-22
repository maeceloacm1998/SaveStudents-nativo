package com.example.savestudents.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.savestudents.model.TimelineItem
import com.example.savestudents.model.repository.ITimelineRepository
import com.example.savestudents.model.viewModel.ITimelineViewModel
import com.example.savestudents.repository.TimelineRepository
import com.example.savestudents.service.external.model.FirebaseResponseModel
import com.example.savestudents.service.external.model.OnFailureModel

class TimelineViewModel : ViewModel(), ITimelineViewModel {
    private val repository: ITimelineRepository = TimelineRepository()

    private val mInformationData = MutableLiveData<TimelineItem>()
    var informationData: LiveData<TimelineItem> = mInformationData

    override fun getTimelineList(collectionPath: String, subjectId: String) {
        repository.getInformation(
            collectionPath,
            object : FirebaseResponseModel<List<TimelineItem>> {
                override fun onSuccess(model: List<TimelineItem>) {
                    mInformationData.value = filterTimelineInformation(model, subjectId)
                }

                override fun onFailure(error: OnFailureModel) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun filterTimelineInformation(timelineList: List<TimelineItem>, subjectId: String): TimelineItem {
        return timelineList.first { it.subjectsInformation?.id == subjectId }
    }
}