package com.savestudents.features.accountRegister.di

import com.savestudents.features.accountRegister.ui.AccountRegisterContract
import com.savestudents.features.accountRegister.ui.AccountRegisterFragment
import com.savestudents.features.accountRegister.ui.AccountRegisterPresenter
import com.savestudents.features.login.ui.LoginContract
import com.savestudents.features.login.ui.LoginFragment
import com.savestudents.features.login.ui.LoginPresenter
import org.koin.dsl.module

object AccountRegisterDependencyInjection {
    private val accountRegisterModules = module {
        factory<AccountRegisterContract.Presenter> { (view: AccountRegisterFragment) -> AccountRegisterPresenter(view, get()) }
    }

    val modules = arrayOf(accountRegisterModules)
}