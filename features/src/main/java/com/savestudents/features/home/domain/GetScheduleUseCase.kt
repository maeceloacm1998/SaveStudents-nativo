package com.savestudents.features.home.domain

import com.google.firebase.firestore.toObject
import com.savestudents.core.utils.DateUtils
import com.savestudents.features.addMatter.models.Event
import com.savestudents.features.addMatter.models.Event.EventItem
import com.savestudents.features.curriculum.ui.models.CurriculumEventCalendar
import com.savestudents.features.home.data.HomeRepository

class GetScheduleUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Result<List<Event>> {
        homeRepository.handleFetchCalendar()
            .onSuccess { documentSnapshot ->
                val calendarEvent =
                    checkNotNull(documentSnapshot.toObject<CurriculumEventCalendar>()).events

                val eventList = handleEvents(calendarEvent)
                return Result.success(eventList)
            }

        return Result.failure(Exception("Error fetching calendar"))
    }

    private fun handleEvents(eventList: List<EventItem>): List<Event> {
        val daysOfWeek = DateUtils.getWeekDatesTimestamp()

        val newEventList: MutableList<Event> = mutableListOf()

        homeRepository.handleDayOfTypeList().forEach { day ->
            val events = eventList.filter { it.dayOfWeek == day && daysOfWeek.contains(it.timestamp)}
            if (events.isNotEmpty()) {
                val event = onCreateEvent(day, events)
                newEventList.add(event)
            } else {
                val event = onCreateEvent(day, mutableListOf())
                newEventList.add(event)
            }
        }

        return newEventList
    }

    private fun onCreateEvent(
        day: String,
        events: List<EventItem>
    ): Event {
        return Event(
            dayName = day,
            events = events.toMutableList()
        )
    }
}