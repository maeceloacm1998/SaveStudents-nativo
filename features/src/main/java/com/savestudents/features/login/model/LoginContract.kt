package com.savestudents.features.login.model

import com.savestudents.features.mvp.BasePresenter
import com.savestudents.features.mvp.BaseView

interface LoginContract {

    interface View: BaseView<Presenter> {
        fun loadingValidateAccount(loading: Boolean)
        fun showValidateAccountError()
        fun successValidateAccount()
    }

    interface Presenter : BasePresenter<View> {
        fun validateAccount()
    }
}