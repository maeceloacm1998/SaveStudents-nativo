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
        val userId: String? = accountManager.getUserAccount()?.id
        userId?.let {
            client.getSpecificDocument("scheduleUser", it).onSuccess {
                val eventList = it.toObject<Schedule>()?.data
                val eventsOfWeek = eventList?.let { events -> removeEventsNotOfTheWeek(events) }
                view.setEventList(eventsOfWeek!!)
            }
        }
    }

    private fun removeEventsNotOfTheWeek(eventList: List<Event>): List<Event> {
        eventList.forEach { event -> event.events = getEventsItems(event.events) }

        return eventList
    }

    private fun getEventsItems(events: List<Event.EventItem>): MutableList<Event.EventItem> {
        val daysOfWeek = DateUtils.getWeekDatesTimestamp()
        val eventItemList: MutableList<Event.EventItem> = mutableListOf()

        events.forEach { item ->
            val isEvent = item.type == EventType.EVENT.value
            val existEventInWeek = daysOfWeek.contains(item.timestamp)

            eventItemList.add(item)

            if (isEvent && !existEventInWeek) {
                eventItemList.remove(item)
            }
        }

        return eventItemList
    }
}