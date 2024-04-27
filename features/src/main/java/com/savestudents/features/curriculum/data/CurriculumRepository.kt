package com.savestudents.features.curriculum.data

import com.google.firebase.firestore.DocumentSnapshot

interface CurriculumRepository {

    suspend fun handleCreateMatter()
    suspend fun handleFetchMatters(): Result<DocumentSnapshot>
    suspend fun handleGetAllWeekDay(dayOfWeek: String): List<Triple<Int, Int, Int>>
}