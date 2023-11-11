package com.savestudents.features.config.ui

import com.savestudents.features.mvp.BasePresenter
import com.savestudents.features.mvp.BaseView

interface ConfigContract {
    interface View : BaseView<Presenter> {
    }

    interface Presenter : BasePresenter {
    }
}