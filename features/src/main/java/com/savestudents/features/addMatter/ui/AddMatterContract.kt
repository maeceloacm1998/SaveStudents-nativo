package com.savestudents.features.addMatter.ui

import com.savestudents.features.addMatter.models.Matter
import com.savestudents.features.mvp.BasePresenter
import com.savestudents.features.mvp.BaseView

interface AddMatterContract {

    interface View : BaseView<Presenter> {
        fun setMatterOptions(matterList: List<String>)
        fun handleMatterSelect(matter: Matter)
        fun loading(loading: Boolean)
        fun loadingRegister(loading: Boolean)
        fun showError()
        fun errorMatterNotSelected(visibility: Boolean)
        fun errorDaysNotSelected(visibility: Boolean)
        fun errorInitialHourNotSelected(visibility: Boolean)
        fun errorFinalHourNotSelected(visibility: Boolean)
    }

    interface Presenter : BasePresenter {
        suspend fun fetchMatters()
        fun matterSelect(option: String)
        fun saveInitialHourSelected(time: String)
        fun saveFinalHourSelected(time: String)
        suspend fun registerMatter(daysSelected: List<String>)
    }
}