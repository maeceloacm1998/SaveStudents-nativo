package com.savestudents.features.event.ui

import com.google.firebase.firestore.ktx.toObject
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.firebase.FirebaseClient
import com.savestudents.core.utils.DateUtils
import com.savestudents.features.addMatter.models.Event
import com.savestudents.features.addMatter.models.EventType
import com.savestudents.features.addMatter.models.Schedule
import java.util.UUID

class EventPresenter(
    private val view: EventContract.View,
    private val client: FirebaseClient,
    private val accountManager: AccountManager
) : EventContract.Presenter {
    override fun start() {}

    override suspend fun validateEvent(eventName: String, date: Long?) {
        when {
            eventName.isEmpty() -> view.showEventNameError()
            date == null -> view.showDateSelectedError()
            else -> register(eventName, date)
        }
    }

    private suspend fun register(eventName: String, dateSelected: Long) {
        val userId: String = checkNotNull(accountManager.getUserAccount()?.id)
        view.loading(true)
        client.getSpecificDocument("scheduleUser", userId).onSuccess {
            val schedule = it.toObject<Schedule>()
            val eventList: List<Event> =
                addEvent(schedule = schedule, eventName = eventName, dateSelected = dateSelected)

            client.setSpecificDocument(
                "scheduleUser", userId, schedule?.copy(data = eventList)
            ).onSuccess {
                view.run {
                    showSnackBarSuccess(eventName)
                    goToCurriculum()
                }
            }.onFailure {
                view.run {
                    hideEventNameError()
                    hideEventNameError()
                    loading(false)
                    showSnackBarError(eventName)
                }
            }
        }
    }

    private fun addEvent(schedule: Schedule?, eventName: String, dateSelected: Long): List<Event> {
        val eventList: List<Event> = requireNotNull(schedule?.data)

        eventList.map { event ->
            if (isDaySelected(event, dateSelected)) {
                val eventItem: Event.EventItem = Event.EventItem(
                    id = UUID.randomUUID().toString(),
                    type = EventType.EVENT.value,
                    matterName = eventName,
                    timestamp = dateSelected,
                    period = null,
                    initialTime = null,
                    finalTime = null
                )
                event.events.add(eventItem)
            }
        }

        return eventList
    }

    private fun isDaySelected(event: Event, dateSelected: Long): Boolean {
        return DateUtils.getDayOfWeekFromTimestamp(dateSelected) == event.dayName
    }
}