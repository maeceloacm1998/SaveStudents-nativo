package com.savestudents.features.curriculum.ui

import com.google.firebase.firestore.ktx.toObject
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.firebase.FirebaseClient
import com.savestudents.core.utils.DateUtils.extractYearMonthDay
import com.savestudents.features.addMatter.models.Schedule

class CurriculumPresenter(
    private val view: CurriculumContract.View,
    private val client: FirebaseClient,
    private val accountManager: AccountManager
) : CurriculumContract.Presenter {
    override fun start() {}

    override suspend fun fetchMatters() {
        val userId: String = accountManager.getUserAccount()?.id.toString()
        client.getSpecificDocument("scheduleUser", userId).onSuccess {
            val schedule: Schedule = checkNotNull(it.toObject())

            schedule.data.forEach { event ->
                event.events.forEach { eventItem ->
                    eventItem.matter?.timelineList?.forEach { timelineItem ->
                        val (year, month, day) = extractYearMonthDay(timelineItem.date ?: 0)
                        view.setEvent(year, month, day)
                    }
                }
            }

        }.onFailure { }
    }
}