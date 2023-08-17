package com.savestudents.features.login.ui

import com.savestudents.features.login.model.LoginContract

class LoginPresenter: LoginContract.Presenter {
    override lateinit var view: LoginContract.View

    override fun start() {}

    override fun  validateAccount() {

    }
}