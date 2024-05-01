package com.savestudents.features.home.ui

import com.savestudents.features.home.domain.GetScheduleUseCase

class HomePresenter(
    private val view: HomeContract.View,
    private val getScheduleUseCase: GetScheduleUseCase
) : HomeContract.Presenter {
    override fun start() {
        view.run {
            onLoading(true)
            onError(false)
            onSetupViewsHomeAdapter()
            onSetupViewsErrorScreen()
        }
    }

    override suspend fun handleEvents() {
        getScheduleUseCase()
            .onSuccess {
                view.run {
                    onLoading(false)
                    onSetEventList(it)
                }
            }
            .onFailure {
                view.onError(true)
            }
    }

    override suspend fun handleRetryEvents() {
        view.run {
            onError(false)
            onLoading(true)
        }
    }
}