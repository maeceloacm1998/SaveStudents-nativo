package com.savestudents.features.curriculum.ui

import com.google.firebase.firestore.ktx.toObject
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.firebase.FirebaseClient
import com.savestudents.core.utils.DateUtils.NORMAL_DATE
import com.savestudents.core.utils.DateUtils.formatDate
import com.savestudents.core.utils.DateUtils.formatDateWithPattern
import com.savestudents.core.utils.DateUtils.getDateWithTimestamp
import com.savestudents.core.utils.DateUtils.getDayOfWeekFromTimestamp
import com.savestudents.core.utils.DateUtils.getWeeksList
import com.savestudents.features.addMatter.models.Event
import com.savestudents.features.addMatter.models.EventType
import com.savestudents.features.addMatter.models.Schedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
class CurriculumPresenter(
    private val view: CurriculumContract.View,
    private val client: FirebaseClient,
    private val accountManager: AccountManager
) : CurriculumContract.Presenter {
    private var eventList: List<Event> = mutableListOf()

    override fun start() {
        view.loadingScreen(true)
    }

    override suspend fun fetchMatters(month: Int) {
        val userId: String = accountManager.getUserAccount()?.id.toString()
        client.getSpecificDocument("scheduleUser", userId).onSuccess {
            val schedule: Schedule = checkNotNull(it.toObject())
            eventList = schedule.data

            schedule.data.forEach { event ->
                event.events.forEach { item ->
                    when (item.type) {
                        EventType.EVENT.value -> handleEvent(checkNotNull(item.timestamp))
                        EventType.MATTER.value -> handleMatter(event, month)
                    }
                }
            }

            view.loadingScreen(false)
        }.onFailure {
            view.error()
        }
    }

    private fun handleEvent(timestamp: Long) {
        val (year, month, day) = getDateWithTimestamp(timestamp)
        view.setEvent(year, month - 1, day)
    }

    private suspend fun handleMatter(event: Event, month: Int) {
        val weekList: List<Triple<Int, Int, Int>> = withContext(Dispatchers.IO) {
            getWeeksList(event.dayName)
        }

        weekList.forEach { (dayWeek, monthWeek, yearWeek) ->
            if (monthWeek == month) view.setEvent(yearWeek, monthWeek, dayWeek)
        }
    }

    override suspend fun fetchEventsWithDate(timestamp: Long) {
        eventList.forEach { event ->
            val weekName = getDayOfWeekFromTimestamp(timestamp)
            if (weekName == event.dayName) handleEventsWithDate(event, timestamp)
        }
    }

    private suspend fun handleEventsWithDate(event: Event, timestamp: Long) {
        val allDaysOfWeek: List<Triple<Int, Int, Int>> = getAllDaysOfWeek(event)
        val eventItemList = removeEventWithDayNotSelected(event.events, timestamp)

        if (eventItemList.isEmpty()) {
            view.showNotEvents(true)
        } else {
            allDaysOfWeek.forEach { (dayWeek, monthWeek, yearWeek) ->
                if (formatDateWithPattern(NORMAL_DATE, timestamp) == formatDate(dayWeek, monthWeek, yearWeek)) {
                    val (_, month, day) = getDateWithTimestamp(timestamp)
                    view.run {
                        updateEventList(eventItemList, day, month)
                        showNotEvents(false)
                        return
                    }
                }
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