package com.savestudents.features.addMatter.ui

import com.google.firebase.firestore.ktx.toObject
import com.savestudents.components.snackbar.SnackBarCustomType
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.firebase.FirebaseClient
import com.savestudents.features.addMatter.models.Schedule
import com.savestudents.features.addMatter.models.Event
import com.savestudents.features.addMatter.models.EventType
import com.savestudents.features.addMatter.models.Matter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddMatterPresenter(
    val view: AddMatterContract.View,
    private val client: FirebaseClient,
    private val accountManager: AccountManager
) : AddMatterContract.Presenter {
    private var matterList: List<Matter> = mutableListOf()
    private var matterSelected: Matter? = null
    private var initialTime: String? = null
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

    override suspend fun validateMatter(daysSelected: List<String>) {
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

            else -> {
                view.run {
                    errorMatterNotSelected(false)
                    errorDaysNotSelected(false)
                    errorInitialHourNotSelected(false)
                }
                registerMatter(daysSelected)
            }
        }
    }

    override suspend fun validateAddMatterOption(matterName: String, period: String) {
        when {
            matterName.isEmpty() -> view.errorAddMatterOptionMatterNameNotSelected(true)
            period.isEmpty() -> view.errorAddMatterOptionPeriodNotSelected(true)
            else -> {
                view.run {
                    errorAddMatterOptionMatterNameNotSelected(false)
                    errorAddMatterOptionPeriodNotSelected(false)
                }
                registerMatterOption(matterName, period)
            }
        }
    }

    private suspend fun registerMatterOption(matterName: String, period: String) {

        val id = withContext(Dispatchers.IO) {
            client.createDocument("matterList", {})
        }

        val matter = Matter(
            id = id.getOrNull().toString(),
            matterName = matterName,
            period = period,
        )
        if (id.isSuccess) {
            client.setSpecificDocument("matterList", id.getOrNull().toString(), matter)
                .onSuccess {
                    view.showSnackbarAddMatterOption(
                        MESSAGE_SUCCESS_ADD_MATTER_OPTION,
                        SnackBarCustomType.SUCCESS
                    )
                }
                .onFailure {
                    view.showSnackbarAddMatterOption(
                        MESSAGE_ERROR_ADD_MATTER_OPTION,
                        SnackBarCustomType.ERROR
                    )
                }
        }
    }

    override fun getInitialHour(): Int {
        return initialTime?.split(":")?.get(0)?.toInt() ?: 0
    }

    override fun getInitialMinutes(): Int {
        return initialTime?.split(":")?.get(1)?.toInt() ?: 0
    }

    suspend fun registerMatter(daysSelected: List<String>) {
        val userId: String = checkNotNull(accountManager.getUserAccount()?.id)
        client.getSpecificDocument("scheduleUser", userId).onSuccess {
            val schedule = it.toObject<Schedule>()
            val eventList: List<Event> = addEvents(schedule = schedule, daysSelected = daysSelected)

            client.setSpecificDocument(
                "scheduleUser",
                userId,
                schedule?.copy(data = eventList)
            ).onSuccess {
                view.showSnackbarStatus(
                    matterName = matterSelected?.matterName,
                    snackBarCustomType = SnackBarCustomType.SUCCESS
                )
                view.goToCurriculum()
            }.onFailure {
                view.showSnackbarStatus(
                    matterName = null,
                    snackBarCustomType = SnackBarCustomType.ERROR
                )
                view.loadingRegister(false)
            }
        }
    }

    private fun addEvents(schedule: Schedule?, daysSelected: List<String>): List<Event> {
        val eventList: List<Event> = requireNotNull(schedule?.data)

        eventList.map { event ->
            if (isDaySelected(event, daysSelected)) {
                checkNotNull(matterSelected).let {
                    val eventItem: Event.EventItem = Event.EventItem(
                        id = it.id,
                        type = EventType.MATTER.value,
                        matterName = it.matterName,
                        period = it.period,
                        initialTime = initialTime.toString()
                    )
                    event.events.add(eventItem)
                }
            }
        }

        return eventList
    }

    private fun isDaySelected(event: Event, daysSelected: List<String>): Boolean {
        return daysSelected.contains(event.dayName)
    }

    companion object {
        private const val MESSAGE_SUCCESS_ADD_MATTER_OPTION = "Sucesso ao cadastrar a matéria"
        private const val MESSAGE_ERROR_ADD_MATTER_OPTION = "Erro ao cadastrar a matéria"
    }
}