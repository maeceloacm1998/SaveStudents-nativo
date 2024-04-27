package com.savestudents.features.curriculum.domain

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.toObject
import com.savestudents.core.utils.DateUtils.getLocalDateWithTimestamp
import com.savestudents.features.addMatter.models.Event.EventItem
import com.savestudents.features.curriculum.data.CurriculumRepository
import com.savestudents.features.curriculum.ui.models.CurriculumEventCalendar
import java.time.LocalDate

class GetEventsToDateSelectedUseCase(
    private val curriculumRepository: CurriculumRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(dateSelected: LocalDate): Result<List<EventItem>> {
        curriculumRepository.handleFetchMatters()
            .onSuccess {
                val events = checkNotNull(it.toObject<CurriculumEventCalendar>()).events
                val filterEvents = events.filter { event ->
                    getLocalDateWithTimestamp(event.timestamp) == dateSelected
                }

                if(filterEvents.isEmpty()){
                    return Result.failure(Throwable("No events found for selected date"))
                }

                return Result.success(filterEvents)
            }

        return Result.failure(Throwable("Error get events of selected date"))
    }
}