package com.savestudents.core.accountManager

import com.google.firebase.auth.FirebaseUser

interface AccountManager {
    suspend fun login(
        email: String,
        password: String,
    ): Result<FirebaseUser>
}