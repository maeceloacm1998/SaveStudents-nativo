package br.oficial.savestudents.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.oficial.savestudents.repository.TimelineRepository
import com.example.data_transfer.model.OnFailureModel
import com.example.data_transfer.model.TimelineItem
import com.example.data_transfer.model.repository.FirebaseResponseModel
import com.example.data_transfer.model.repository.ITimelineRepository
import com.example.data_transfer.model.viewModel.ITimelineViewModel

class TimelineViewModel : ViewModel(), ITimelineViewModel {
    private val repository: ITimelineRepository = TimelineRepository()

    private val mInformationData = MutableLiveData<TimelineItem>()
    var informationData: LiveData<TimelineItem> = mInformationData

    override fun getTimelineList(collectionPath: String, subjectId: String) {
        repository.getInformation(
            collectionPath,
            object : FirebaseResponseModel<List<TimelineItem>> {
                override fun onSuccess(model: List<TimelineItem>) {
                    val timelineItem = filterTimelineInformation(model, subjectId)
                    timelineItem.timelineList?.sortBy { it.date }

                    mInformationData.value = timelineItem
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