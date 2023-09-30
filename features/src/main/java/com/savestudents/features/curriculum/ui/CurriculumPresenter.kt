package com.savestudents.features.curriculum.ui

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.ktx.toObject
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.firebase.FirebaseClient
import com.savestudents.core.utils.DateUtils.extractYearMonthDay
import com.savestudents.core.utils.DateUtils.timestampsEquals
import com.savestudents.features.addMatter.models.Event
import com.savestudents.features.addMatter.models.EventType
import com.savestudents.features.addMatter.models.Schedule

class CurriculumPresenter(
    private val view: CurriculumContract.View,
    private val client: FirebaseClient,
    private val accountManager: AccountManager
) : CurriculumContract.Presenter {
    private var eventList: List<Event> = mutableListOf()

    override fun start() {}

    override suspend fun fetchMatters() {
        val userId: String = accountManager.getUserAccount()?.id.toString()
        client.getSpecificDocument("scheduleUser", userId).onSuccess {
            val schedule: Schedule = checkNotNull(it.toObject())
            eventList = schedule.data

            schedule.data.forEach { event ->
                event.events.forEach { eventItem ->
                    eventItem.matter?.timelineList?.forEach { timelineItem ->
                        val (year, month, day) = extractYearMonthDay(timelineItem.date)
                        view.setEvent(year, month, day)
                    }
                }
            }

        }.onFailure { }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun fetchEventsWithDate(timestamp: Long) {
        eventList.forEach { event ->
            event.events.forEach { eventItem ->
                eventItem.matter?.timelineList?.forEach { timelineItem ->
                    if (timestampsEquals(timelineItem.date, timestamp)) {
                        view.updateEventList(
                            matterName = eventItem.matter.matterName,
                            eventType = EventType.valueOf(eventItem.type),
                            initialTime = eventItem.initialTime,
                            timelineList = timelineItem
                        )
                    }
                }
            }
        }
    }
}