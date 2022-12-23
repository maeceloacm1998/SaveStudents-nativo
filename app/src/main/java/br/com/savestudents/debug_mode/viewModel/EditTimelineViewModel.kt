package br.com.savestudents.debug_mode.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.savestudents.debug_mode.repository.EditTimelineRepository
import br.com.savestudents.dto.TimelineDTO
import br.com.savestudents.dto.asModel
import br.com.savestudents.model.TimelineItem
import br.com.savestudents.service.external.model.FirebaseResponseModel
import br.com.savestudents.service.external.model.OnFailureModel

class EditTimelineViewModel: ViewModel() {
    private val repository = EditTimelineRepository()

    private var mTimelineItem = MutableLiveData<TimelineItem>()
    var timelineItem: LiveData<TimelineItem> = mTimelineItem

    private var mUpdateTimelineItem = MutableLiveData<Boolean>()
    var updateTimelineItem: LiveData<Boolean> = mUpdateTimelineItem

    fun getSubjectItemPerId(id: String) {
        repository.getSubjectPerId(id, object: FirebaseResponseModel<TimelineDTO> {
            override fun onSuccess(model: TimelineDTO) {
                mTimelineItem.value = model.asModel()
            }

            override fun onFailure(error: OnFailureModel) {}
        })
    }

    fun updateTimelineItem(timelineItem: TimelineItem) {
        repository.updateTimelineItem(timelineItem, object : FirebaseResponseModel<Boolean> {
            override fun onSuccess(model: Boolean) {
                mUpdateTimelineItem.value = model
            }

            override fun onFailure(error: OnFailureModel) {
                TODO("Not yet implemented")
            }
        })
    }
}