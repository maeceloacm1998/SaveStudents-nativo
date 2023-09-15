package com.savestudents.core.sharedPreferences

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object SharedPreferencesDependencyInjection {
    val modules = arrayOf(module {
        single<SharedPreferencesBuilder> { SharedPreferencesBuilderImpl(androidContext()) }
    })
}