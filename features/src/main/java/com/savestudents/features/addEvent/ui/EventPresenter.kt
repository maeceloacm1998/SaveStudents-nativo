package com.savestudents.features.addEvent.ui

import com.savestudents.features.addEvent.domain.CreateEventUseCase

class EventPresenter(
    private val view: EventContract.View,
    private val createEventUseCase: CreateEventUseCase
) : EventContract.Presenter {
    override fun start() {
        view.run {
            onSetupViewsCreateEventButton()
            onSetupViewsSelectedDate()
        }
    }

    override suspend fun handleValidateEvent(eventName: String, dateSelected: Long?) {
        when {
            eventName.isEmpty() -> view.showEventNameError()
            dateSelected == null -> view.showDateSelectedError()
            else -> onCreateEvent(eventName =eventName, dateSelected = dateSelected)
        }
    }

    private suspend fun onCreateEvent(eventName: String, dateSelected: Long) {
        createEventUseCase(eventName, dateSelected).onSuccess {
            view.run {
                showSnackBarSuccess(eventName)
                goToCurriculum()
            }
        }.onFailure {
            view.run {
                hideEventNameError()
                hideEventNameError()
                onLoading(false)
                showSnackBarError(eventName)
            }
        }
    }
}