package com.savestudents.features.login.model

import com.savestudents.features.mvp.BasePresenter
import com.savestudents.features.mvp.BaseView

interface LoginContract {

    interface View : BaseView<Presenter> {
        fun loadingValidateAccount(loading: Boolean)
        fun showEmptyEmailError()
        fun showIncorrectEmailError()
        fun showEmptyPasswordError()
        fun showValidateAccountError()
        fun hideValidateAccountError()
        fun successValidateAccount()
    }

    interface Presenter : BasePresenter {
        suspend fun validateAccount(email: String, password: String)
    }
}