package com.savestudents.core.notification

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object NotificationManagerDependencyInjection {
    val modules = arrayOf(module {
        single<NotificationManager> { NotificationManagerImpl(androidContext()) }
    })
}