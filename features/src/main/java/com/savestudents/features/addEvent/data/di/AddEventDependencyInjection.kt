package com.savestudents.features.addEvent.data.di

import com.savestudents.features.addEvent.data.AddEventRepository
import com.savestudents.features.addEvent.data.AddEventRepositoryImpl
import com.savestudents.features.addEvent.domain.CreateEventUseCase
import com.savestudents.features.addEvent.ui.EventContract
import com.savestudents.features.addEvent.ui.EventFragment
import com.savestudents.features.addEvent.ui.EventPresenter
import org.koin.dsl.module

object AddEventDependencyInjection {
    private val addEventModules = module {
        factory<AddEventRepository> {
            AddEventRepositoryImpl(
                client = get(),
                accountManager = get()
            )
        }

        factory { CreateEventUseCase(addEventRepository = get()) }

        factory<EventContract.Presenter> { (view: EventFragment) ->
            EventPresenter(
                view = view,
                createEventUseCase = get()
            )
        }
    }

    val modules = arrayOf(addEventModules)
}