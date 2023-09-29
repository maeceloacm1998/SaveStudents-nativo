package com.savestudents.features.curriculum.ui

import com.savestudents.features.mvp.BasePresenter
import com.savestudents.features.mvp.BaseView

interface CurriculumContract {
    interface View : BaseView<Presenter> {
        fun setEvent(year: Int, month: Int, day: Int)
    }

    interface Presenter : BasePresenter {
        suspend fun fetchMatters()
    }
}