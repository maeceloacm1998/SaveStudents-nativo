package com.savestudents.features.addMatter.ui

import com.savestudents.components.snackbar.SnackBarCustomType
import com.savestudents.features.addMatter.models.Matter
import com.savestudents.core.mvp.BasePresenter
import com.savestudents.core.mvp.BaseView

interface AddMatterContract {

    interface View : BaseView<Presenter> {
        fun onSetupViewsTextLayout()
        fun onSetupViewsAddMatter()
        fun onSetupViewsCreateMatter()
        fun onSetupViewsMatterInput()
        fun onSetMatterOptions(matterList: List<String>)
        fun onMatterSelect(matter: Matter)
        fun onLoading(loading: Boolean)
        fun onLoadingRegister(loading: Boolean)
        fun showError()
        fun showErrorMatterNotSelected(visibility: Boolean)
        fun showErrorDaysNotSelected(visibility: Boolean)
        fun showErrorInitialHourNotSelected(visibility: Boolean)
        fun showErrorAddMatterOptionMatterNameNotSelected(visibility: Boolean)
        fun showErrorAddMatterOptionPeriodNotSelected(visibility: Boolean)
        fun showSnackbarStatus(matterName: String?, snackBarCustomType: SnackBarCustomType)
        fun showSnackbarAddMatterOption(message: String, snackBarCustomType: SnackBarCustomType)
        fun goToCurriculum()
    }

    interface Presenter : BasePresenter {
        suspend fun handleFetchMatters()
        suspend fun handleValidateMatter(daysSelected: List<String>)
        suspend fun handleValidateAddMatterOption(matterName: String, period: String)
        fun onMatterSelect(option: String)
        fun onSaveInitialHourSelected(time: String)
        fun onGetInitialHour(): Int
        fun onGetInitialMinutes(): Int
    }
}