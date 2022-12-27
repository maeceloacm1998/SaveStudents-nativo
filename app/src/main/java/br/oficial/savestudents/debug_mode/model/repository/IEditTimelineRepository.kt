package br.oficial.savestudents.debug_mode.model.repository

import br.oficial.savestudents.dto.TimelineDTO
import br.oficial.savestudents.model.TimelineItem
import br.oficial.savestudents.service.external.model.FirebaseResponseModel

interface IEditTimelineRepository {
    fun getSubjectPerId(id:String, firebaseResponseModel: FirebaseResponseModel<TimelineDTO>)
    fun updateTimelineItem(timelineItem: TimelineItem, firebaseResponseModel: FirebaseResponseModel<Boolean>)
}