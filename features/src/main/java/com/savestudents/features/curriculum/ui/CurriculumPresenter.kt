package com.savestudents.features.curriculum.ui

import com.google.firebase.firestore.ktx.toObject
import com.savestudents.components.calendar.EventCalendar
import com.savestudents.components.calendar.EventCalendarType
import com.savestudents.components.snackbar.SnackBarCustomType
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.firebase.FirebaseClient
import com.savestudents.core.utils.DateUtils.NORMAL_DATE
import com.savestudents.core.utils.DateUtils.formatDateWithPattern
import com.savestudents.core.utils.DateUtils.getDateWithTimestamp
import com.savestudents.core.utils.DateUtils.getDayOfWeekFromTimestamp
import com.savestudents.core.utils.DateUtils.getWeeksList
import com.savestudents.features.R
import com.savestudents.features.addMatter.models.Event
import com.savestudents.features.addMatter.models.EventType
import com.savestudents.features.addMatter.models.Schedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class CurriculumPresenter(
    private val view: CurriculumContract.View,
    private val client: FirebaseClient,
    private val accountManager: AccountManager
) : CurriculumContract.Presenter {
    private var eventList: List<Event> = mutableListOf()
    private var eventCalendarList: MutableList<EventCalendar> = mutableListOf()
    private var selectedDate: Long = 0L
    override fun start() {
        view.loadingScreen(true)
    }

    override suspend fun fetchMatters() {
        defaultEvents()

        val userId: String = accountManager.getUserAccount()?.id.toString()
        client.getSpecificDocument("scheduleUser", userId).onSuccess {
            val schedule: Schedule = checkNotNull(it.toObject())
            eventList = schedule.data

            schedule.data.forEach { event ->
                event.events.forEach { item ->
                    handleMatter(event)
                    handleEvent(checkNotNull(item.timestamp))
                }
            }

            view.updateCalendar(eventCalendarList)
            view.loadingScreen(false)
        }.onFailure {
            view.error()
        }
    }

    private fun defaultEvents() {
        eventList = mutableListOf()
        eventCalendarList = mutableListOf()
    }

    private fun handleEvent(timestamp: Long) {
        val (year, month, day) = getDateWithTimestamp(timestamp)
        val event = EventCalendar(
            date = LocalDate.of(year, month, day),
            eventCalendarType = mutableListOf(EventCalendarType.EVENT)
        )

        addEvent(event = event, type = EventCalendarType.EVENT)
    }

    private fun handleMatter(event: Event) {
        val containsMatter = event.events.filter { it.type == EventType.MATTER.value }
        if (event.events.isNotEmpty() && containsMatter.isNotEmpty()) {
            val weekList: List<Triple<Int, Int, Int>> = getWeeksList(event.dayName)

            weekList.forEach { (dayWeek, monthWeek, yearWeek) ->
                val newEvent = EventCalendar(
                    date = LocalDate.of(yearWeek, monthWeek + 1, dayWeek),
                    eventCalendarType = mutableListOf(EventCalendarType.MATTER)
                )

                addEvent(event = newEvent, type = EventCalendarType.MATTER)
            }
        }
    }

    override suspend fun fetchEventsWithDate(timestamp: Long) {
        selectedDate = timestamp
        eventList.forEach { event ->
            val weekName = getDayOfWeekFromTimestamp(timestamp)
            if (weekName == event.dayName) handleEventsWithDate(event, timestamp)
        }
    }

    override suspend fun deleteEvent(eventItem: Event.EventItem) {
        val userId: String = checkNotNull(accountManager.getUserAccount()?.id)
        val weekName = getDayOfWeekFromTimestamp(selectedDate)

        eventList.forEach { event ->
            if (event.dayName == weekName) event.events.remove(eventItem)
        }

        client.setSpecificDocument(
            "scheduleUser",
            userId,
            Schedule(userId = userId, data = eventList)
        ).onSuccess {
            view.run {
                showSnackBar(
                    R.string.curriculum_success_remove_event,
                    SnackBarCustomType.SUCCESS
                )
                start()
                clearCalendarEvents()
                fetchMatters()
                fetchEventsWithDate(selectedDate)
            }
        }.onFailure {
            view.showSnackBar(
                R.string.curriculum_error_remove_event,
                SnackBarCustomType.ERROR
            )
        }
    }

    private fun existEvent(event: EventCalendar): Boolean {
        return eventCalendarList.any { it.date == event.date }
    }

    private fun addEvent(event: EventCalendar, type: EventCalendarType) {
        if (existEvent(event)) {
            addEventWhenExist(event.date, type)
        } else {
            eventCalendarList.add(event)
        }
    }

    private fun addEventWhenExist(eventDate: LocalDate, eventType: EventCalendarType) {
        val oldEvent = eventCalendarList.first { it.date == eventDate }
        eventCalendarList.remove(oldEvent)
        oldEvent.eventCalendarType.add(eventType)
        eventCalendarList.add(oldEvent)
    }

    private fun handleEventsWithDate(event: Event, timestamp: Long) {
        val eventItemList = removeEventWithDayNotSelected(event.events, timestamp)

        if (eventItemList.isEmpty()) {
            view.showNotEvents(true)
        } else {
            val (_, month, day) = getDateWithTimestamp(timestamp)
            view.run {
                updateEventList(eventItemList, day, month)
                showNotEvents(false)
                return
            }
        }
    }

    private fun removeEventWithDayNotSelected(
        eventItemList: MutableList<Event.EventItem>,
        timestamp: Long
    ): MutableList<Event.EventItem> {
        val newEventItemList: MutableList<Event.EventItem> = mutableListOf()
        val dateSelected = formatDateWithPattern(NORMAL_DATE, timestamp)
        eventItemList.forEach { eventItem ->
            newEventItemList.add(eventItem)
            if (eventItem.type == EventType.EVENT.value) {
                val dateEvent =
                    formatDateWithPattern(NORMAL_DATE, checkNotNull(eventItem.timestamp))
                if (dateEvent != dateSelected) newEventItemList.remove(eventItem)
            }
        }
        return newEventItemList
    }

    private suspend fun getAllDaysOfWeek(event: Event) = withContext(Dispatchers.IO) {
        getWeeksList(event.dayName).map { (day, month, year) -> Triple(day, month + 1, year) }
    }
}