package com.savestudents.features.addMatter.data

import com.google.firebase.firestore.DocumentSnapshot
import com.savestudents.features.addMatter.models.Matter
import com.savestudents.features.curriculum.ui.models.CurriculumEventCalendar

interface AddMatterRepository {
    suspend fun handleMatters(): Result<List<DocumentSnapshot>>
    suspend fun handleSaveMatter(matter: Matter): Result<Boolean>
    suspend fun handleFetchCalendarUser(): Result<DocumentSnapshot>
    suspend fun handleSaveCalendarUser(calendar: CurriculumEventCalendar): Result<Boolean>
}