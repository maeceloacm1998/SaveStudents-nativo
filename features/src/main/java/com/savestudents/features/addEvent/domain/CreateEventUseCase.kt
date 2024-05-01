package com.savestudents.features.addEvent.domain

import com.google.firebase.firestore.toObject
import com.savestudents.core.utils.DateUtils
import com.savestudents.core.utils.DateUtils.getDayOfWeekFromTimestamp
import com.savestudents.features.addEvent.data.AddEventRepository
import com.savestudents.features.addMatter.models.Event.EventItem
import com.savestudents.features.addMatter.models.EventType
import com.savestudents.features.curriculum.ui.models.CurriculumEventCalendar
import org.koin.mp.KoinPlatformTools.generateId

class CreateEventUseCase(
    private val addEventRepository: AddEventRepository
) {
    suspend operator fun invoke(
        eventName: String,
        timestamp: Long
    ): Result<Boolean> {
        getCalendarUser()
            .onSuccess { result ->
                val eventListItem = handleEventDays(
                    eventItemList = result,
                    eventName = eventName,
                    timestamp = timestamp
                )

                return saveData(
                    calendar = CurriculumEventCalendar(
                        events = eventListItem
                    )
                )
            }

        return Result.failure(Exception("Error creating event"))
    }

    private fun handleEventDays(
        eventItemList: List<EventItem>,
        eventName: String,
        timestamp: Long
    ): List<EventItem> {
        val newEventItemList = eventItemList.toMutableList()
        val eventItem = onCreateEventList(
            eventName = eventName,
            timestamp = timestamp
        )

        newEventItemList.add(eventItem)

        return newEventItemList
    }

    private fun onCreateEventList(
        eventName: String,
        timestamp: Long
    ): EventItem {
        val dayOfWeek = getDayOfWeekFromTimestamp(timestamp)
        return EventItem(
            id = generateId(),
            type = EventType.EVENT.value,
            matterName = eventName,
            dayOfWeek = dayOfWeek,
            timestamp = timestamp
        )
    }


    private suspend fun getCalendarUser(): Result<List<EventItem>> {
        addEventRepository.handleFetchCalendarUser()
            .onSuccess {
                return Result.success(checkNotNull(it.toObject<CurriculumEventCalendar>()).events)
            }

        return Result.failure(Exception("Error getting data"))
    }

    private suspend fun saveData(calendar: CurriculumEventCalendar): Result<Boolean> {
        addEventRepository.handleSaveCalendarUser(calendar)
            .onSuccess {
                return Result.success(true)
            }

        return Result.failure(Exception("Error saving data"))
    }
}