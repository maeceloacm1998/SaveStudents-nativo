package com.savestudents.core.notificationworker.di

import android.content.Context
import androidx.work.WorkManager
import com.savestudents.core.notificationworker.NotificationWorkerClient
import com.savestudents.core.notificationworker.NotificationWorkerClientImpl
import com.savestudents.core.notificationworker.worker.NotificationWorkerBuilderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object NotificationWorkerDependencyInjection {
    private val notificationWorkerModules = module {
        single { provideWorker(androidContext()) }
        single<NotificationWorkerClient> { NotificationWorkerClientImpl(get()) }
        single { NotificationWorkerBuilderImpl(androidContext(), get()) }
    }

    val modules = arrayOf(notificationWorkerModules)

    private fun provideWorker(context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }
}