package com.savestudents.features.addMatter.ui

import com.google.firebase.firestore.ktx.toObject
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.firebase.FirebaseClient
import com.savestudents.features.addMatter.models.Matter
import com.savestudents.features.addMatter.models.Schedule
import com.savestudents.features.addMatter.models.Event
import com.savestudents.features.addMatter.models.EventType
import java.util.Calendar

class AddMatterPresenter(
    val view: AddMatterContract.View,
    private val client: FirebaseClient,
    private val accountManager: AccountManager
) : AddMatterContract.Presenter {
    private var matterList: List<Matter> = mutableListOf()
    private var matterSelected: Matter? = null
    private var initialTime: String? = null
    private var finalTime: String? = null

    override fun start() {
        view.loading(true)
    }

    override suspend fun fetchMatters() {
        client.getDocumentValue("matterList").onSuccess {
            val matterListItems =
                it.map { item -> checkNotNull(item.toObject(Matter::class.java)?.matterName) }
            matterList = it.map { item -> checkNotNull(item.toObject(Matter::class.java)) }
            view.setMatterOptions(matterListItems)
            view.loading(false)
        }.onFailure {
            view.showError()
        }
    }

    override fun matterSelect(option: String) {
        val matter = checkNotNull(matterList.find { matter -> matter.matterName == option })
        view.handleMatterSelect(matter)
        matterSelected = matter
    }

    override fun saveInitialHourSelected(time: String) {
        initialTime = time
    }

    override fun saveFinalHourSelected(time: String) {
        finalTime = time
    }

    override suspend fun registerMatter(daysSelected: List<String>) {
        when {
            matterSelected == null -> {
                view.run {
                    errorMatterNotSelected(true)
                    loadingRegister(false)
                }
            }

            daysSelected.isEmpty() -> {
                view.run {
                    errorDaysNotSelected(true)
                    loadingRegister(false)
                }
            }

            initialTime == null -> {
                view.run {
                    errorInitialHourNotSelected(true)
                    loadingRegister(false)
                }
            }

            finalTime == null -> {
                view.run {
                    errorFinalHourNotSelected(true)
                    loadingRegister(false)
                }
            }

            else -> {
                view.run {
                    errorMatterNotSelected(false)
                    errorDaysNotSelected(false)
                    errorFinalHourNotSelected(false)
                    errorInitialHourNotSelected(false)
                }
                register(daysSelected)
            }
        }
    }

    suspend fun register(daysSelected: List<String>) {
        val userId: String = checkNotNull(accountManager.getUserAccount()?.id)
        client.getSpecificDocument("scheduleUser", userId).onSuccess {
            val schedule = it.toObject<Schedule>()
            val eventList: List<Event> = addEvents(schedule = schedule, daysSelected = daysSelected)

            client.setSpecificDocument(
                "scheduleUser",
                userId,
                schedule?.copy(data = eventList)
            )
        }
    }

    private fun addEvents(schedule: Schedule?, daysSelected: List<String>): List<Event> {
        val eventList: List<Event> = requireNotNull(schedule?.data)

        eventList.map { event ->
            if (isDaySelected(event, daysSelected)) {
                val eventItem: Event.EventItem = Event.EventItem(
                    date = Calendar.getInstance().timeInMillis,
                    type = EventType.MATTER.value,
                    matter = matterSelected,
                    initialTime = initialTime.toString(),
                    finalTime = finalTime.toString()
                )
                event.events.add(eventItem)
            }
        }

        return eventList
    }

    private fun isDaySelected(event: Event, daysSelected: List<String>): Boolean {
        return daysSelected.contains(event.dayName)
    }
}