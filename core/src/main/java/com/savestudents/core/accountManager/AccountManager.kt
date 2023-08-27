package com.savestudents.core.accountManager

import com.google.firebase.auth.FirebaseUser
import com.savestudents.core.accountManager.model.UserAccount

interface AccountManager {
    suspend fun login(
        email: String,
        password: String,
    ): Result<FirebaseUser>
    suspend fun register(user: UserAccount, password: String): Result<FirebaseUser>
}