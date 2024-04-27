package com.savestudents.features.curriculum.data

import com.google.firebase.firestore.DocumentSnapshot
import com.savestudents.features.curriculum.ui.models.CurriculumEventCalendar

interface CurriculumRepository {

    suspend fun handleCreateMatter()
    suspend fun handleFetchMatters(): Result<DocumentSnapshot>
    suspend fun handleUpdateMatter(curriculumEventCalendar: CurriculumEventCalendar): Result<Boolean>
    suspend fun handleGetAllWeekDay(dayOfWeek: String): List<Triple<Int, Int, Int>>
}