package com.savestudents.core.accountManager

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.savestudents.core.accountManager.model.UserAccount
import com.savestudents.core.firebase.FirebaseClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class AccountManagerImpl(
    private val auth: FirebaseAuth,
    private val client: FirebaseClient
) : AccountManager {
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

    override suspend fun register(user: UserAccount, password: String): Result<FirebaseUser> {
        return try {
            val res = withContext(Dispatchers.IO) {
                auth.createUserWithEmailAndPassword(user.email, password).await()
            }

            val id = withContext(Dispatchers.IO) {
                client.createDocument("user", user)
            }

            withContext(Dispatchers.IO) {
                client.setSpecificDocument(
                    "user",
                    checkNotNull(id.getOrNull()),
                    user.copy(id = checkNotNull(id.getOrNull()))
                )
            }

            checkNotNull(res.user).run {
                Log.d("SUCCESS REGISTER USER", res.user?.displayName.toString())
                firebaseUser = checkNotNull(res.user)
                Result.success(checkNotNull(res.user))
            }
        } catch (e: Exception) {
            Log.e("ERROR REGISTER USER", e.cause.toString())
            Result.failure(Throwable(e.message, e.cause))
        }
    }
}