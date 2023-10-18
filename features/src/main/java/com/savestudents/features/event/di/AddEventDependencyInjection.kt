package com.savestudents.features.event.di

import com.savestudents.features.event.ui.EventContract
import com.savestudents.features.event.ui.EventFragment
import com.savestudents.features.event.ui.EventPresenter
import org.koin.dsl.module

object AddEventDependencyInjection {
    private val addEventModules = module {
        factory<EventContract.Presenter> { (view: EventFragment) ->
            EventPresenter(view, get(), get())
        }
    }

    val modules = arrayOf(addEventModules)
}