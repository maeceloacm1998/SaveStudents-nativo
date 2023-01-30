package br.oficial.savestudents.debug_mode.model.repository

import com.example.data_transfer.dto.TimelineDTO
import com.example.data_transfer.model.TimelineItem
import com.example.data_transfer.model.repository.FirebaseResponseModel

interface IEditTimelineRepository {
    fun getSubjectPerId(id:String, firebaseResponseModel: FirebaseResponseModel<TimelineDTO>)
    fun updateTimelineItem(timelineItem: TimelineItem, firebaseResponseModel: FirebaseResponseModel<Boolean>)
}