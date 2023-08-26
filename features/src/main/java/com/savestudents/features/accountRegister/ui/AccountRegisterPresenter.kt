package com.savestudents.features.accountRegister.ui

import com.savestudents.core.accountManager.AccountManager
import com.savestudents.features.accountRegister.model.UserAccount

class AccountRegisterPresenter(
    private val view: AccountRegisterContract.View,
    private val accountManager: AccountManager
): AccountRegisterContract.Presenter {
    override fun start() {
        getInstitutions()
    }

    override fun fetchInstitution() {
        TODO("Not yet implemented")
    }

    override fun validateFields(user: UserAccount) {
        TODO("Not yet implemented")
    }

    override fun saveData(user: UserAccount, password: String) {
        TODO("Not yet implemented")
    }

    private fun getInstitutions() {

    }
}