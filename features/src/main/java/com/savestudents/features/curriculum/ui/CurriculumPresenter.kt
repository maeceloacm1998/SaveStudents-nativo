package com.savestudents.features.curriculum.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.savestudents.components.snackbar.SnackBarCustomType
import com.savestudents.core.utils.DateUtils
import com.savestudents.core.utils.DateUtils.getDateWithTimestamp
import com.savestudents.core.utils.DateUtils.getLocalDateWithTimestamp
import com.savestudents.features.R
import com.savestudents.features.addMatter.models.Event
import com.savestudents.features.curriculum.domain.DeleteEventUseCase
import com.savestudents.features.curriculum.domain.GetEventsToDateSelectedUseCase
import com.savestudents.features.curriculum.domain.GetMattersUseCase
import java.time.LocalDate

class CurriculumPresenter(
    private val view: CurriculumContract.View,
    private val getMattersUseCase: GetMattersUseCase,
    private val getEventsToDateSelectedUseCase: GetEventsToDateSelectedUseCase,
    private val deleteEventUseCase: DeleteEventUseCase
) : CurriculumContract.Presenter {

    override fun start() {
        view.loadingScreen(true)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun fetchMatters() {
        getMattersUseCase()
            .onSuccess { eventCalendarList ->
                view.updateCalendar(eventCalendarList)
                view.loadingScreen(false)
            }
            .onFailure {
                view.error()
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun fetchEventsWithDate(timestamp: Long) {
        val (_, month, day) = getDateWithTimestamp(timestamp)
        val selectedDate = getLocalDateWithTimestamp(timestamp)

        getEventsToDateSelectedUseCase(selectedDate)
            .onSuccess { eventsOfDate ->
                view.updateEventList(
                    eventList = eventsOfDate,
                    day = day,
                    month = month
                )
                view.showNotEvents(false)
            }
            .onFailure {
                view.showNotEvents(true)
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun deleteEvent(eventItem: Event.EventItem) {
        deleteEventUseCase(eventForDelete = eventItem)
            .onSuccess {
                view.run {
                    showSnackBar(
                        R.string.curriculum_success_remove_event,
                        SnackBarCustomType.SUCCESS
                    )
                    start()
                    clearCalendarEvents()
                    fetchMatters()
                    fetchEventsWithDate(eventItem.timestamp)
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
}