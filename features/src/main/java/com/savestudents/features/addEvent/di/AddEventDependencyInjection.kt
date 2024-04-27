package com.savestudents.features.addEvent.di

import com.savestudents.features.addEvent.ui.EventContract
import com.savestudents.features.addEvent.ui.EventFragment
import com.savestudents.features.addEvent.ui.EventPresenter
import org.koin.dsl.module

object AddEventDependencyInjection {
    private val addEventModules = module {
        factory<EventContract.Presenter> { (view: EventFragment) ->
            EventPresenter(view, get(), get())
        }
    }

    val modules = arrayOf(addEventModules)
}