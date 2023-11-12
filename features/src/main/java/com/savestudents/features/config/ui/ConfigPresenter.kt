package com.savestudents.features.config.ui

import com.savestudents.core.accountManager.AccountManager

class ConfigPresenter(
    private val view: ConfigContract.View,
    private val accountManager: AccountManager
) : ConfigContract.Presenter {
    override fun start() {}
    override fun handleSecurityConfig() {
        view.goToSecurityConfig()
    }

    override fun handleNotificationConfig() {
        view.goToNotificationConfig()
    }

    override fun handleExitApp() {
        accountManager.logoutUser()
        view.onLogout()
    }

}