package com.savestudents.features.home.ui

import com.savestudents.features.home.domain.GetScheduleUseCase

class HomePresenter(
    private val view: HomeContract.View,
    private val getScheduleUseCase: GetScheduleUseCase
) : HomeContract.Presenter {
    override fun start() {
        view.onLoading(true)
        view.onSetupViewsHomeAdapter()
    }

    override suspend fun handleEvents() {
        getScheduleUseCase()
            .onSuccess {
                view.onSetEventList(it)
            }
            .onFailure {
                val x = ""
            }
    }
}