package com.savestudents.features.home.data

import com.google.firebase.firestore.DocumentSnapshot
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.firebase.FirebaseClient
import com.savestudents.core.utils.DateUtils
import com.savestudents.core.utils.DaysType

class HomeRepositoryImpl(
    private val client: FirebaseClient,
    private val accountManager: AccountManager
) : HomeRepository {
    override suspend fun handleFetchCalendar(): Result<DocumentSnapshot> {
        return client.getSpecificDocument(
            collectionPath = "calendarUser",
            documentPath = accountManager.getUserId()
        )
    }

    override fun handleDayOfTypeList(): List<String> {
        return DaysType.getDayOfWeekList()
    }

    override fun getDaysCurrentDate(): List<Long> {
        return DateUtils.getWeekDatesTimestamp()
    }
}