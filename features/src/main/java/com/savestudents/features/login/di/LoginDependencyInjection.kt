package com.savestudents.features.login.di

import com.savestudents.features.login.model.LoginContract
import com.savestudents.features.login.ui.LoginFragment
import com.savestudents.features.login.ui.LoginPresenter
import org.koin.dsl.module

object LoginDependencyInjection {
    private val loginModules = module {
        factory<LoginContract.Presenter> { (view: LoginFragment) -> LoginPresenter(view, get()) }
    }

    val modules = arrayOf(loginModules)
}