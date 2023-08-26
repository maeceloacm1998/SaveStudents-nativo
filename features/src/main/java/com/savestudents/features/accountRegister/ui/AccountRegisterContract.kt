package com.savestudents.features.accountRegister.ui

import com.savestudents.features.accountRegister.model.UserAccount
import com.savestudents.features.mvp.BasePresenter
import com.savestudents.features.mvp.BaseView

interface AccountRegisterContract {

    interface View : BaseView<Presenter> {
        fun setInstitutionList(institutions: List<String>)
        fun showEmailFormatError()
        fun showBirthdateFormatError()
        fun showEmptyNameField()
        fun showEmptyEmailField()
        fun showEmptyInstitutionField()
        fun showEmptyBirthDateField()
        fun showEmptyPasswordField()
        fun clearFieldsError()
        fun loading(loading: Boolean)
    }

    interface Presenter : BasePresenter {
        suspend fun fetchInstitution()
        fun validateFields(user: UserAccount, password: String)
        fun saveData(user: UserAccount, password: String)
    }
}