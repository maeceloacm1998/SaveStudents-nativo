package br.oficial.savestudents.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.oficial.savestudents.model.TimelineItem
import br.oficial.savestudents.model.repository.ITimelineRepository
import br.oficial.savestudents.model.viewModel.ITimelineViewModel
import br.oficial.savestudents.repository.TimelineRepository
import br.oficial.savestudents.service.external.model.FirebaseResponseModel
import br.oficial.savestudents.service.external.model.OnFailureModel

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