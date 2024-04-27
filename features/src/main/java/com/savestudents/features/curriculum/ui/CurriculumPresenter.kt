package com.savestudents.features.curriculum.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.ktx.toObject
import com.savestudents.components.calendar.EventCalendar
import com.savestudents.components.calendar.EventCalendarType
import com.savestudents.components.snackbar.SnackBarCustomType
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.firebase.FirebaseClient
import com.savestudents.core.utils.DateUtils
import com.savestudents.core.utils.DateUtils.NORMAL_DATE
import com.savestudents.core.utils.DateUtils.formatDateWithPattern
import com.savestudents.core.utils.DateUtils.getDateWithTimestamp
import com.savestudents.core.utils.DateUtils.getDayOfWeekFromTimestamp
import com.savestudents.core.utils.DateUtils.getTimestampWithDate
import com.savestudents.core.utils.DateUtils.getWeeksList
import com.savestudents.features.R
import com.savestudents.features.addMatter.models.Event
import com.savestudents.features.addMatter.models.EventType
import com.savestudents.features.addMatter.models.Schedule
import com.savestudents.features.curriculum.domain.GetMattersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class CurriculumPresenter(
    private val view: CurriculumContract.View,
    private val getMattersUseCase: GetMattersUseCase,
    private val client: FirebaseClient,
    private val accountManager: AccountManager
) : CurriculumContract.Presenter {
    private var eventList: List<Event> = mutableListOf()
    private var selectedDate: Long = 0L
    override fun start() {
        view.loadingScreen(true)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun fetchMatters() {
        defaultEvents()

        getMattersUseCase()
            .onSuccess { eventCalendarList ->
                view.updateCalendar(eventCalendarList)
                view.loadingScreen(false)
            }
            .onFailure {
                view.error()
            }
    }

    private fun defaultEvents() {
        eventList = mutableListOf()
    }

    override suspend fun fetchEventsWithDate() {
        val (day, month, year) = DateUtils.getCurrentDate()
        val currentDateTimestamp = getTimestampWithDate(day, month, year)

        selectedDate = currentDateTimestamp
        eventList.forEach { event ->
            val weekName = getDayOfWeekFromTimestamp(currentDateTimestamp)
            if (weekName == event.dayName) {
                handleEventsWithDate(event, currentDateTimestamp)
            }
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
                fetchEventsWithDate()
            }
        }.onFailure {
            view.showSnackBar(
                R.string.curriculum_error_remove_event,
                SnackBarCustomType.ERROR
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSetCurrentDate() {
        val (day, month, year) = DateUtils.getCurrentDate()
        view.onSetCurrentDate(LocalDate.of(year, month, day))
    }

    private suspend fun handleEventsWithDate(event: Event, timestamp: Long) {
        val allDaysOfWeek: List<Triple<Int, Int, Int>> = getAllDaysOfWeek(event)
        val eventItemList = removeEventWithDayNotSelected(event.events, timestamp)

        if (eventItemList.isEmpty()) {
            view.showNotEvents(true)
        } else {
            allDaysOfWeek.forEach { (dayWeek, monthWeek, yearWeek) ->
                val eventDate = formatDateWithPattern(NORMAL_DATE, timestamp)
                val weekDate = formatDateWithPattern(
                    NORMAL_DATE,
                    getTimestampWithDate(dayWeek, monthWeek, yearWeek)
                )

                if (eventDate == weekDate) {
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