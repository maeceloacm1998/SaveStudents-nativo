package com.savestudents.features.securityconfig.di

import com.savestudents.features.securityconfig.ui.SecurityConfigContract
import com.savestudents.features.securityconfig.ui.SecurityConfigFragment
import com.savestudents.features.securityconfig.ui.SecurityConfigPresenter
import org.koin.dsl.module

object SecurityConfigDependencyInjection {
    private val securityModules = module {
        factory<SecurityConfigContract.Presenter> { (view: SecurityConfigFragment) ->
            SecurityConfigPresenter(view, get())
        }
    }

    val modules = arrayOf(securityModules)
}