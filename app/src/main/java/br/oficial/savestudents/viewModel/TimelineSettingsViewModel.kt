package br.oficial.savestudents.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.oficial.savestudents.model.NotificationTimeline
import br.oficial.savestudents.repository.TimelineSettingsRepository
import br.oficial.savestudents.service.external.model.FirebaseResponseModel
import br.oficial.savestudents.service.external.model.OnFailureModel

class TimelineSettingsViewModel : ViewModel() {
    private val repository = TimelineSettingsRepository()

    private val mActiveNotificationTimeline = MutableLiveData<Boolean>()
    val activeNotificationTimeline = mActiveNotificationTimeline

    fun setTimelineNotification(documentPath: String, notificationItem: NotificationTimeline) {
        repository.setNotificationTimeline(documentPath, notificationItem)
    }

    fun deleteTimelineNotification(id: String) {
        repository.deleteNotificationTimeline(id)
    }

    fun existNotificationTimeline(id: String) {
        repository.existNotificationTimeline(
            id,
            object : FirebaseResponseModel<NotificationTimeline> {
                override fun onSuccess(model: NotificationTimeline) {
                    mActiveNotificationTimeline.value = existNotificationTimelineItem(model, id)
                }

                override fun onFailure(error: OnFailureModel) {}
            })
    }


    private fun existNotificationTimelineItem(
        notificationTimeline: NotificationTimeline,
        id: String
    ): Boolean {
        return notificationTimeline.id == id
    }
}