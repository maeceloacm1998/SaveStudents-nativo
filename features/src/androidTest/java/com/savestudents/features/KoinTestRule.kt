package com.savestudents.features

import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.FirebaseApp
import com.savestudents.core.accountManager.AccountManagerDependencyInjection
import com.savestudents.features.login.di.LoginDependencyInjection
import com.savestudents.features.shared.utils.KoinUtils
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class KoinTestRule() : TestWatcher() {
    override fun starting(description: Description) {
        startKoin {
            androidContext(InstrumentationRegistry.getInstrumentation().targetContext.applicationContext)
            KoinUtils.addModules(module {
                single {
                    FirebaseApp.initializeApp(InstrumentationRegistry.getInstrumentation().targetContext.applicationContext);
                }
            })
            KoinUtils.addModules(*AccountManagerDependencyInjection.modules)
            KoinUtils.addModules(*LoginDependencyInjection.modules)
        }
    }

    override fun finished(description: Description) {
        stopKoin()
    }
}