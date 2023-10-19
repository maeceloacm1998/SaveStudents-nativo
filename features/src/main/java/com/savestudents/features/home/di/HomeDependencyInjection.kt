package com.savestudents.features.home.di

import com.savestudents.features.home.ui.HomeContract
import com.savestudents.features.home.ui.HomeFragment
import com.savestudents.features.home.ui.HomePresenter
import org.koin.dsl.module

object HomeDependencyInjection {
    private val HomeModules = module {
        factory<HomeContract.Presenter> { (view: HomeFragment) ->
            HomePresenter(view, get(), get())
        }
    }

    val modules = arrayOf(HomeModules)
}