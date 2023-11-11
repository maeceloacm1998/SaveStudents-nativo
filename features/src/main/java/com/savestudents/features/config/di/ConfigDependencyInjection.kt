package com.savestudents.features.config.di

import com.savestudents.features.config.ui.ConfigContract
import com.savestudents.features.config.ui.ConfigFragment
import com.savestudents.features.config.ui.ConfigPresenter
import org.koin.dsl.module

object ConfigDependencyInjection {
    private val configModules = module {
        factory<ConfigContract.Presenter> { (view: ConfigFragment) ->
            ConfigPresenter(view)
        }
    }
    val modules = arrayOf(configModules)
}