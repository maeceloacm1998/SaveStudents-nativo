package com.savestudents.features.shared.utils

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

object KoinUtils {
    private var mKoinApplication: KoinApplication? = null

    fun addModules(vararg modules: Module) {
        loadKoinModules(modules.asList())
    }

    fun removeModules(vararg modules: Module) {
        unloadKoinModules(modules.asList())
    }

    fun createInstance(context: Context) {
        if (mKoinApplication == null) {
            mKoinApplication = startKoin {
                androidContext(context)
            }
        }
    }
}