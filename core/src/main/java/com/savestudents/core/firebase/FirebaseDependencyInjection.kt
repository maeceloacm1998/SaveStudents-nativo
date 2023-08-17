package com.savestudents.core.firebase

import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module

object FirebaseDependencyInjection {
    val modules = arrayOf(module {
        single { provideFirebase() }
        single<FirebaseClient> { FirebaseClientImpl(get()) }
    })

    private fun provideFirebase(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}