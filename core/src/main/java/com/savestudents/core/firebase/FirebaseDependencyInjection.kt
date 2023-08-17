package com.savestudents.core.firebase

import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module

object FirebaseDependencyInjection {
    val modules = arrayOf(module {
        factory { provideFirebase() }
        single { FirebaseClientImpl(get()) }
    })

    private fun provideFirebase(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}