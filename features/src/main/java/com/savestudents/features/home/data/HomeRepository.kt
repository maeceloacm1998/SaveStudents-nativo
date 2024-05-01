package com.savestudents.features.home.data

import com.google.firebase.firestore.DocumentSnapshot

interface HomeRepository {
    suspend fun handleFetchCalendar(): Result<DocumentSnapshot>
    fun handleDayOfTypeList(): List<String>
    fun getDaysCurrentDate(): List<Long>
}