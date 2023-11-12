package com.savestudents.features.config.ui

import com.savestudents.core.accountManager.AccountManager

class ConfigPresenter(
    private val view: ConfigContract.View,
    private val accountManager: AccountManager
) : ConfigContract.Presenter {
    override fun start() {}
    override fun handleSecurityConfig() {
        TODO("Not yet implemented")
    }

    override fun handleNotificationConfig() {
        TODO("Not yet implemented")
    }

    override fun handleExitApp() {
        accountManager.logoutUser()
        view.onLogout()
    }

}