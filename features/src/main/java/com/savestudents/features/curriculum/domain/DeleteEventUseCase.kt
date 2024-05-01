package com.savestudents.features.curriculum.domain

import com.google.firebase.firestore.toObject
import com.savestudents.features.addMatter.models.Event.EventItem
import com.savestudents.features.addMatter.models.EventType.EVENT
import com.savestudents.features.addMatter.models.EventType.MATTER
import com.savestudents.features.curriculum.data.CurriculumRepository
import com.savestudents.features.curriculum.ui.models.CurriculumEventCalendar

class DeleteEventUseCase(
    private val curriculumRepository: CurriculumRepository
) {
    suspend operator fun invoke(eventForDelete: EventItem): Result<Boolean> {
        curriculumRepository.handleFetchMatters()
            .onSuccess {
                val events = checkNotNull(it.toObject<CurriculumEventCalendar>()?.events)

                when (eventForDelete.type) {
                    EVENT.value -> {
                        val newEventList = onDeleteEventType(
                            events = events,
                            eventForDelete = eventForDelete
                        )

                        return handleFetchUpdateMatters(newEventList)
                    }

                    MATTER.value -> {
                        val newEventList = onDeleteMatterType(
                            events = events,
                            eventForDelete = eventForDelete
                        )
                        return handleFetchUpdateMatters(newEventList)
                    }
                }
            }

        return Result.failure(Throwable("Error update events"))
    }

    private fun onDeleteEventType(
        events: List<EventItem>,
        eventForDelete: EventItem
    ): List<EventItem> {
        return events.filter { it.id != eventForDelete.id }
    }

    private fun onDeleteMatterType(
        events: List<EventItem>,
        eventForDelete: EventItem
    ): List<EventItem> {
        return events.filter { it.matterName != eventForDelete.matterName }
    }

    private suspend fun handleFetchUpdateMatters(
        events: List<EventItem>
    ): Result<Boolean> {
        val curriculumCalendar = CurriculumEventCalendar(
            events = events
        )
        curriculumRepository.handleUpdateMatter(curriculumCalendar)
            .onSuccess {
                return Result.success(it)
            }

        return Result.failure(Throwable("Error update events"))
    }
}