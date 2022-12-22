package br.com.savestudents.debug_mode.model.repository

import br.com.savestudents.dto.TimelineDTO
import br.com.savestudents.service.external.model.FirebaseResponseModel

interface IEditTimelineRepository {
    fun getSubjectPerId(id:String, firebaseResponseModel: FirebaseResponseModel<TimelineDTO>)
}