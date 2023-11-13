package com.savestudents.features.securityconfig.ui

import com.savestudents.core.accountManager.AccountManager

class SecurityConfigPresenter(
    private val view: SecurityConfigContract.View,
    private val accountManager: AccountManager
) : SecurityConfigContract.Presenter {
    override fun start() {
        fetchUser()
    }

    override fun fetchUser() {
        val user = accountManager.getUserAccount()
        view.run {
            handleEmail(user?.email.orEmpty())
            handleName(user?.name.orEmpty())
            handleDate(user?.birthDate.orEmpty())
        }
    }
}