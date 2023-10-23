package com.savestudents.features.addMatter.ui

import com.savestudents.components.snackbar.SnackBarCustomType
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
        fun errorAddMatterOptionMatterNameNotSelected(visibility: Boolean)
        fun errorAddMatterOptionPeriodNotSelected(visibility: Boolean)
        fun showSnackbarStatus(matterName: String?, snackBarCustomType: SnackBarCustomType)
        fun showSnackbarAddMatterOption(message: String, snackBarCustomType: SnackBarCustomType)
        fun goToCurriculum()
    }

    interface Presenter : BasePresenter {
        suspend fun fetchMatters()
        suspend fun validateMatter(daysSelected: List<String>)
        suspend fun validateAddMatterOption(matterName: String, period: String)
        fun matterSelect(option: String)
        fun saveInitialHourSelected(time: String)
        fun getInitialHour(): Int
        fun getInitialMinutes(): Int
    }
}