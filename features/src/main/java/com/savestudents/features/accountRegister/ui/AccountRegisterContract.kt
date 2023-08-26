package com.savestudents.features.accountRegister.ui

import com.savestudents.features.accountRegister.model.UserAccount
import com.savestudents.features.mvp.BasePresenter
import com.savestudents.features.mvp.BaseView

interface AccountRegisterContract {

    interface View : BaseView<Presenter> {
        fun showEmailFormatError()
        fun showBirthdateFormatError()
        fun showEmptyNameField()
        fun showEmptyEmailField()
        fun showEmptyInstitutionField()
        fun showEmptyBirthDateField()
        fun showEmptyPasswordField()
    }

    interface Presenter : BasePresenter {
        fun fetchInstitution()
        fun validateFields(user: UserAccount)
        fun saveData(user: UserAccount, password: String)
    }
}