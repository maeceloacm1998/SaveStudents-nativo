package com.savestudents.core.accountManager

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class AccountManagerImpl(private val auth: FirebaseAuth) : AccountManager {
    private lateinit var firebaseUser: FirebaseUser

    override suspend fun login(
        email: String,
        password: String,
    ): Result<FirebaseUser> {
        return try {
            val res = withContext(Dispatchers.IO) {
                auth.signInWithEmailAndPassword(email, password).await()
            }

            checkNotNull(res.user).run {
                Log.d("SUCCESS LOGIN", res.user?.displayName.toString())
                firebaseUser = checkNotNull(res.user)
                Result.success(checkNotNull(res.user))
            }
        } catch (e: Exception) {
            Log.e("ERROR LOGIN", e.cause.toString())
            Result.failure(Throwable(e.message, e.cause))

        }
    }
}