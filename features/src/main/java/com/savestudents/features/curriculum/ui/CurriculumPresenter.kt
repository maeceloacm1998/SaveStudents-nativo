package com.savestudents.features.curriculum.ui

import com.google.firebase.firestore.ktx.toObject
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.firebase.FirebaseClient
import com.savestudents.core.utils.DateUtils
import com.savestudents.features.addMatter.models.Event
import com.savestudents.features.addMatter.models.Schedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CurriculumPresenter(
    private val view: CurriculumContract.View,
    private val client: FirebaseClient,
    private val accountManager: AccountManager
) : CurriculumContract.Presenter {
    private var eventList: List<Event> = mutableListOf()

    override fun start() {}

    override suspend fun fetchMatters() {
        val userId: String = accountManager.getUserAccount()?.id.toString()
        client.getSpecificDocument("scheduleUser", userId).onSuccess {
            val schedule: Schedule = checkNotNull(it.toObject())
            eventList = schedule.data

            schedule.data.forEach { event ->
                event.events.forEach { _ ->
                    val weekList: List<Triple<Int, Int, Int>> = withContext(Dispatchers.IO) {
                        DateUtils.getWeeksList(event.dayName)
                    }

                    weekList.forEach { (day, month, year) ->
                        view.setEvent(year, month, day)
                    }
                }
            }

        }.onFailure { }
    }

    override suspend fun fetchEventsWithDate(day: Int, month: Int, year: Int) {
        eventList.forEach { event ->
            val allDaysOfWeek: List<Triple<Int, Int, Int>> = withContext(Dispatchers.IO) {
                DateUtils.getWeeksList(event.dayName)
            }

            allDaysOfWeek.forEach { (dayWeek, monthWeek, yearWeek) ->
                if (DateUtils.formatDate(day, month, year) == DateUtils.formatDate(dayWeek, monthWeek, yearWeek)) {
                    view.updateEventList(event.events, day, month)
                }
            }
        }
    }
}