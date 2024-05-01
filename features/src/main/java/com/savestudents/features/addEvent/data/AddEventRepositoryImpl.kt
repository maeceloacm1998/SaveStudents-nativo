package com.savestudents.features.addEvent.data

import com.google.firebase.firestore.DocumentSnapshot
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.firebase.FirebaseClient
import com.savestudents.features.curriculum.ui.models.CurriculumEventCalendar

class AddEventRepositoryImpl(
    private val client: FirebaseClient,
    private val accountManager: AccountManager
): AddEventRepository {
    override suspend fun handleFetchCalendarUser(): Result<DocumentSnapshot> {
        return client.getSpecificDocument(
            collectionPath = "calendarUser",
            documentPath = accountManager.getUserId()
        )
    }

    override suspend fun handleSaveCalendarUser(calendar: CurriculumEventCalendar): Result<Boolean> {
        return client.setSpecificDocument(
            collectionPath = "calendarUser",
            documentPath = accountManager.getUserId(),
            data = calendar
        )
    }
}