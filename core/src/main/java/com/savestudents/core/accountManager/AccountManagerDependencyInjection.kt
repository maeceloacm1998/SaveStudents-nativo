package com.savestudents.core.accountManager

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import org.koin.dsl.module

object AccountManagerDependencyInjection {
    val modules = arrayOf(module {
        factory { provideFirebaseAuth() }
        single<AccountManager> { AccountManagerImpl(get(), get()) }
    })

    private fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }
}