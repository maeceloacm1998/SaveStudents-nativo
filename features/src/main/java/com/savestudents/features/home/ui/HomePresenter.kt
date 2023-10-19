package com.savestudents.features.home.ui

import com.google.firebase.firestore.ktx.toObject
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.firebase.FirebaseClient
import com.savestudents.core.utils.DateUtils
import com.savestudents.features.addMatter.models.Event
import com.savestudents.features.addMatter.models.EventType
import com.savestudents.features.addMatter.models.Schedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class HomePresenter(
    private val view: HomeContract.View,
    private val client: FirebaseClient,
    private val accountManager: AccountManager,
) : HomeContract.Presenter {
    override fun start() {
        view.loading(true)
    }

    override suspend fun getEvents() {
        val userId: String = checkNotNull(accountManager.getUserAccount()?.id)
        client.getSpecificDocument("scheduleUser", userId).onSuccess {
            val eventList = it.toObject<Schedule>()?.data
            eventList?.let { list -> view.setEventList(handleEventsInWeek(list)) }
        }
    }

    private fun handleEventsInWeek(eventList: List<Event>): List<Event> {
        val daysOfWeek = DateUtils.getWeekDatesNormalFormat(DateUtils.getCurrentDate())

        eventList.forEach { event ->
            event.events.forEach { eventItem ->
                if (eventItem.type == EventType.EVENT.value) {
                    val existEvent = daysOfWeek.contains(DateUtils.formatDateWithPattern(DateUtils.NORMAL_DATE, eventItem.timestamp!!))

                    if(!existEvent) {
                        event.events.remove(eventItem)
                    }
                }
            }
        }

        return eventList
    }
}