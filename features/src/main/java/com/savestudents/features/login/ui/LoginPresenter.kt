package com.savestudents.features.login.ui

import com.google.firebase.auth.FirebaseUser
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.firebase.FirebaseResponseModel
import com.savestudents.core.firebase.OnFailureModel
import com.savestudents.features.login.model.LoginContract

class LoginPresenter(private val accountManager: AccountManager) : LoginContract.Presenter {
    override lateinit var view: LoginContract.View

    override fun start() {}

    override fun validateAccount(email: String, password: String) {
        view.loadingValidateAccount(true)

        if (isValidFields(email, password)) {
            view.hideValidateAccountError()
            handleLogin(email, password)
        } else {
            when {
                email.isBlank() -> view.showEmptyEmailError()
                password.isBlank() -> view.showEmptyPasswordError()
                else -> {
                    if (!isValidEmail(email)) view.showIncorrectEmailError()
                }
            }
            view.loadingValidateAccount(false)
        }
    }

    private fun handleLogin(email: String, password: String) {
        accountManager.login(email, password, object : FirebaseResponseModel<FirebaseUser> {
            override fun onSuccess(model: FirebaseUser) {
                view.run {
                    successValidateAccount()
                    loadingValidateAccount(false)
                }
            }

            override fun onFailure(error: OnFailureModel) {
                view.run {
                    showValidateAccountError()
                    loadingValidateAccount(false)
                }
            }
        })
    }

    private fun isValidFields(email: String, password: String): Boolean {
        return email.isNotBlank() && password.isNotBlank()
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}")
        return emailPattern.matches(email)
    }
}