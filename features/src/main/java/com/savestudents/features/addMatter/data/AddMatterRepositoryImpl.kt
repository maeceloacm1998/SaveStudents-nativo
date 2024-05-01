package com.savestudents.features.addMatter.data

import com.google.firebase.firestore.DocumentSnapshot
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.firebase.FirebaseClient
import com.savestudents.features.addMatter.models.Matter
import com.savestudents.features.curriculum.ui.models.CurriculumEventCalendar

class AddMatterRepositoryImpl(
    private val client: FirebaseClient,
    private val accountManager: AccountManager
): AddMatterRepository {
    override suspend fun handleMatters(): Result<List<DocumentSnapshot>> {
        return client.getDocumentValue("matterList")
    }

    override suspend fun handleSaveMatter(matter: Matter): Result<Boolean> {
        return client.setSpecificDocument(
            collectionPath = "matterList",
            documentPath = matter.id,
            data = matter
        )
    }

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