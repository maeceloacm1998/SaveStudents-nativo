package com.savestudents.features.addMatter.domain

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.toObject
import com.savestudents.core.utils.DateUtils.getWeeksList
import com.savestudents.features.addMatter.data.AddMatterRepository
import com.savestudents.features.addMatter.models.Event.EventItem
import com.savestudents.features.addMatter.models.EventType
import com.savestudents.features.addMatter.models.Matter
import com.savestudents.features.curriculum.ui.models.CurriculumEventCalendar

class CreateMatterUseCase(
    private val addMatterRepository: AddMatterRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(
        matter: Matter,
        initialTime: String,
        daysOfWeekSelected: List<String>
    ): Result<Boolean> {
        getCalendarUser()
            .onSuccess { result ->
                val eventListItem = handleMatterDays(
                    eventItemList = result,
                    matter = matter,
                    initialTime = initialTime,
                    daysOfWeekSelected = daysOfWeekSelected
                )

                return saveData(
                    calendar = CurriculumEventCalendar(
                        events = eventListItem
                    )
                )
            }

        return Result.failure(Exception("Error creating matter"))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleMatterDays(
        eventItemList: List<EventItem>,
        matter: Matter,
        initialTime: String,
        daysOfWeekSelected: List<String>
    ): List<EventItem> {
        val newEventItemList = eventItemList.toMutableList()

        daysOfWeekSelected.forEach { dayOfWeek ->
            val dates = getWeeksList(dayOfWeek = dayOfWeek)

            dates.forEach { timestamp ->
                val eventItem = onCreateEventList(
                    matter = matter,
                    initialTime = initialTime,
                    dayOfWeek = dayOfWeek,
                    timestampDate = timestamp
                )

                newEventItemList.add(eventItem)
            }
        }

        return newEventItemList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onCreateEventList(
        matter: Matter,
        initialTime: String,
        dayOfWeek: String,
        timestampDate: Long = 0L
    ): EventItem {
        return EventItem(
            id = matter.id,
            type = EventType.MATTER.value,
            matterName = matter.matterName,
            dayOfWeek = dayOfWeek,
            period = matter.period,
            initialTime = initialTime,
            timestamp = timestampDate
        )
    }


    private suspend fun getCalendarUser(): Result<List<EventItem>> {
        addMatterRepository.handleFetchCalendarUser()
            .onSuccess {
                return Result.success(checkNotNull(it.toObject<CurriculumEventCalendar>()).events)
            }

        return Result.failure(Exception("Error getting data"))
    }

    private suspend fun saveData(calendar: CurriculumEventCalendar): Result<Boolean> {
        addMatterRepository.handleSaveCalendarUser(calendar)
            .onSuccess {
                return Result.success(true)
            }

        return Result.failure(Exception("Error saving data"))
    }
}