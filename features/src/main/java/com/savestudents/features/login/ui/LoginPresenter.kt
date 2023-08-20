package com.savestudents.features.login.ui

import com.savestudents.core.accountManager.AccountManager
import com.savestudents.features.login.model.LoginContract

class LoginPresenter(
    private val view: LoginContract.View,
    private val accountManager: AccountManager
) : LoginContract.Presenter {
    override fun start() {}

    override suspend fun validateAccount(email: String, password: String) {
        view.loadingValidateAccount(true)

        if (isValidFields(email, password)) {
            view.hideValidateAccountError()
            handleLogin(email, password)
        } else {
            when {
                email.isBlank() -> view.showEmptyEmailError()
                password.isBlank() -> view.showEmptyPasswordError()
                else -> view.showIncorrectEmailError()
            }
            view.loadingValidateAccount(false)
        }
    }

    private suspend fun handleLogin(email: String, password: String) {
        val res = accountManager.login(email, password)

        if (res.isFailure) {
            view.run {
                showValidateAccountError()
                loadingValidateAccount(false)
            }
        } else {
            view.run {
                successValidateAccount()
                loadingValidateAccount(false)
            }
        }
    }

    private fun isValidFields(email: String, password: String): Boolean {
        return email.isNotBlank() && password.isNotBlank() && isValidEmail(email)
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}")
        return emailPattern.matches(email)
    }
}