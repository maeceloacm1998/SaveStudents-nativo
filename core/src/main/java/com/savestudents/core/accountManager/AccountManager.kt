package com.savestudents.core.accountManager

import com.google.firebase.auth.FirebaseUser
import com.savestudents.core.firebase.FirebaseResponseModel

interface AccountManager {
    fun login(
        email: String,
        password: String,
        firebaseResponseModel: FirebaseResponseModel<FirebaseUser>
    )
}