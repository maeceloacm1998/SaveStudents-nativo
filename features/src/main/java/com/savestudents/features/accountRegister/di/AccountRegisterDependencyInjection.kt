package com.savestudents.features.accountRegister.di

import com.savestudents.features.accountRegister.ui.AccountRegisterContract
import com.savestudents.features.accountRegister.ui.AccountRegisterFragment
import com.savestudents.features.accountRegister.ui.AccountRegisterPresenter
import org.koin.dsl.module

object AccountRegisterDependencyInjection {
    private val accountRegisterModules = module {
        factory<AccountRegisterContract.Presenter> { (view: AccountRegisterFragment) ->
            AccountRegisterPresenter(view, get(), get())
        }
    }

    val modules = arrayOf(accountRegisterModules)
}