package com.savestudents.features.notificationconfig.di

import com.savestudents.features.notificationconfig.ui.NotificationConfigContract
import com.savestudents.features.notificationconfig.ui.NotificationConfigFragment
import com.savestudents.features.notificationconfig.ui.NotificationConfigPresenter
import org.koin.dsl.module

object NotificationConfigDependencyInjection {
    private val notificationModules = module {
        factory<NotificationConfigContract.Presenter> { (view: NotificationConfigFragment) ->
            NotificationConfigPresenter(view, get())
        }
    }

    val modules = arrayOf(notificationModules)
}