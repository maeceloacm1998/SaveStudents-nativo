package com.savestudents.features.addEvent.data

import com.google.firebase.firestore.DocumentSnapshot
import com.savestudents.features.curriculum.ui.models.CurriculumEventCalendar

interface AddEventRepository {
    suspend fun handleFetchCalendarUser(): Result<DocumentSnapshot>
    suspend fun handleSaveCalendarUser(calendar: CurriculumEventCalendar): Result<Boolean>
}