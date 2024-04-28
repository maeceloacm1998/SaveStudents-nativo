package com.savestudents.features.curriculum.domain

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.ktx.toObject
import com.savestudents.components.calendar.EventCalendar
import com.savestudents.components.calendar.EventCalendarType
import com.savestudents.core.utils.DateUtils
import com.savestudents.features.addMatter.models.Event.EventItem
import com.savestudents.features.curriculum.data.CurriculumRepository
import com.savestudents.features.curriculum.ui.models.CurriculumEventCalendar
import java.time.LocalDate

class GetMattersUseCase(
    private val curriculumRepository: CurriculumRepository
) {
    private val eventList: MutableList<EventCalendar> = mutableListOf()

    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(): Result<List<EventCalendar>> {

        curriculumRepository.handleFetchMatters().onSuccess {
            val events = checkNotNull(it.toObject<CurriculumEventCalendar>()).events

            events.forEach { event ->
                val eventType = EventCalendarType.parse(event.type)
                val eventCalendar = handleCalendarEvent(
                    event = event,
                    eventType = eventType
                )
                addEvent(
                    eventCalendar = eventCalendar,
                    eventType = eventType
                )
            }

            return Result.success(eventList)
        }

        return Result.failure(Throwable("Error get calendar events"))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleCalendarEvent(
        event: EventItem,
        eventType: EventCalendarType
    ): EventCalendar {
        return EventCalendar(
            date = DateUtils.getLocalDateWithTimestamp(event.timestamp),
            eventCalendarType = mutableListOf(eventType)
        )
    }

    private fun existEvent(event: EventCalendar): Boolean {
        return eventList.any { it.date == event.date }
    }

    private fun addEvent(eventCalendar: EventCalendar, eventType: EventCalendarType) {
        if (existEvent(eventCalendar)) {
            addEventWhenExist(eventCalendar.date, eventType)
        } else {
            eventList.add(eventCalendar)
        }
    }

    private fun addEventWhenExist(eventDate: LocalDate, eventType: EventCalendarType) {
        val index = eventList.indexOfFirst { it.date == eventDate }
        if (index != -1) {
            eventList[index].eventCalendarType.add(eventType)
        }
    }
}