package com.savestudents.features.login.di

import com.savestudents.features.login.model.LoginContract
import com.savestudents.features.login.ui.LoginFragment
import com.savestudents.features.login.ui.LoginPresenter
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

object LoginDependencyInjection {
    private val loginModules = module {
        factory { LoginFragment() }
        factory<LoginContract.Presenter> { LoginPresenter(get()) }
    }

    val modules = arrayOf(loginModules)
}