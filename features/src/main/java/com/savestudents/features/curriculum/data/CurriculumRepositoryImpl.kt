package com.savestudents.features.curriculum.data

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.toObject
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.firebase.FirebaseClient
import com.savestudents.core.utils.DateUtils
import com.savestudents.features.addMatter.models.Schedule
import com.savestudents.features.curriculum.ui.models.CurriculumEventCalendar

class CurriculumRepositoryImpl(
    private val client: FirebaseClient,
    private val accountManager: AccountManager
) : CurriculumRepository {
    override suspend fun handleCreateMatter() {
        client.getSpecificDocument("scheduleUser", accountManager.getUserId()).onSuccess {
            val schedule = it.toObject<Schedule>()
            val allEvents = schedule?.data?.flatMap { it.events } ?: emptyList()

            val req = CurriculumEventCalendar(
                events = allEvents
            )

            client.setSpecificDocument(
                collectionPath = "calendarUser",
                documentPath = accountManager.getUserId(), req
            )
        }
    }

    override suspend fun handleFetchMatters(): Result<DocumentSnapshot> {
        return client.getSpecificDocument(
            collectionPath = "calendarUser",
            documentPath = accountManager.getUserId()
        )
    }

    override suspend fun handleUpdateMatter(curriculumEventCalendar: CurriculumEventCalendar): Result<Boolean> {
        return client.setSpecificDocument(
            collectionPath = "calendarUser",
            documentPath = accountManager.getUserId(),
            data = curriculumEventCalendar
        )
    }

    override suspend fun handleGetAllWeekDay(dayOfWeek: String): List<Triple<Int, Int, Int>> {
        return DateUtils.getWeeksList(dayOfWeek)
    }
}