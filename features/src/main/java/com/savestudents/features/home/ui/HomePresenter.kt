package com.savestudents.features.home.ui

import com.google.firebase.firestore.ktx.toObject
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.firebase.FirebaseClient
import com.savestudents.core.utils.DateUtils
import com.savestudents.features.addMatter.models.Event
import com.savestudents.features.addMatter.models.EventType
import com.savestudents.features.addMatter.models.Schedule

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
            val eventsOfWeek =
                checkNotNull(eventList?.let { events -> removeEventsNotOfTheWeek(events) })
            view.setEventList(eventsOfWeek)
        }
    }

    private fun removeEventsNotOfTheWeek(eventList: List<Event>): List<Event> {
        val daysOfWeek = DateUtils.getWeekDatesWithNormalFormat()

        eventList.forEach { event ->
            event.events.forEach { item ->
                val isEvent = item.type == EventType.EVENT.value
                val existEventInWeek = daysOfWeek.contains(
                    DateUtils.formatDateWithPattern(
                        DateUtils.NORMAL_DATE,
                        item.timestamp!!
                    )
                )

                if (isEvent && !existEventInWeek) event.events.remove(item)
            }
        }

        return eventList
    }
}