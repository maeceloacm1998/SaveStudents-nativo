package com.savestudents.core.accountManager

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.toObject
import com.google.gson.Gson
import com.savestudents.core.accountManager.model.UserAccount
import com.savestudents.core.firebase.FirebaseClient
import com.savestudents.core.sharedPreferences.SharedPreferencesBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class AccountManagerImpl(
    private val auth: FirebaseAuth,
    private val client: FirebaseClient,
    private val sharedPreferences: SharedPreferencesBuilder
) : AccountManager {
    private var firebaseUser: FirebaseUser? = null

    override suspend fun login(
        email: String,
        password: String,
    ): Result<FirebaseUser> {
        return try {

            val res = withContext(Dispatchers.IO) {
                auth.signInWithEmailAndPassword(email, password).await()
            }

            val userAccountRes = withContext(Dispatchers.IO) {
                client.getSpecificDocument("user", checkNotNull(res.user?.uid))
            }

            userAccountRes.onSuccess { document ->
                val userDocument = document.toObject<UserAccount>()
                sharedPreferences.putString(USER_ACCOUNT, Gson().toJson(userDocument))
            }

            checkNotNull(res.user).run {
                Log.d("SUCCESS LOGIN", res.user?.displayName.toString())
                firebaseUser = checkNotNull(res.user)
                sharedPreferences.putBoolean(LOGGED_USER, true)
                Result.success(checkNotNull(res.user))
            }
        } catch (e: Exception) {
            Log.e("ERROR LOGIN", e.cause.toString())
            Result.failure(Throwable(e.message, e.cause))
        }
    }

    override suspend fun register(user: UserAccount): Result<FirebaseUser> {
        return try {
            val res = withContext(Dispatchers.IO) {
                auth.createUserWithEmailAndPassword(user.email, user.password).await()
            }

            val userWithId = user.copy(id = checkNotNull(res.user?.uid))

            withContext(Dispatchers.IO) {
                client.setSpecificDocument(
                    "user",
                    checkNotNull(res.user?.uid),
                    userWithId
                )
            }

            checkNotNull(res.user).run {
                Log.d("SUCCESS REGISTER USER", res.user?.displayName.toString())
                firebaseUser = checkNotNull(res.user)
                sharedPreferences.putBoolean(LOGGED_USER, true)
                sharedPreferences.putString(USER_ACCOUNT, Gson().toJson(userWithId))
                Result.success(checkNotNull(res.user))
            }
        } catch (e: Exception) {
            Log.e("ERROR REGISTER USER", e.cause.toString())
            Result.failure(Throwable(e.message, e.cause))
        }
    }

    override fun logoutUser() {
        sharedPreferences.putBoolean(LOGGED_USER, false)
        sharedPreferences.putString(USER_ACCOUNT, "")
    }

    override fun isLoggedUser(): Boolean {
        return sharedPreferences.getBoolean(LOGGED_USER, false)
    }

    override fun getFirebaseUser(): FirebaseUser? {
        return firebaseUser
    }

    override fun getUserAccount(): UserAccount? {
        return sharedPreferences.getString(USER_ACCOUNT, "").run {
            Gson().fromJson(this, UserAccount::class.java)
        }
    }

    override fun getUserId(): String {
        val user = sharedPreferences.getString(USER_ACCOUNT, "").run {
            Gson().fromJson(this, UserAccount::class.java)
        }
        return user.id
    }

    override fun setNotifications(enabled: Boolean) {
        return sharedPreferences.putBoolean(NOTIFICATION_ACCOUNT, enabled)
    }

    override fun isNotificationEnabled(): Boolean {
        return sharedPreferences.getBoolean(NOTIFICATION_ACCOUNT, false)
    }

    companion object {
        private const val LOGGED_USER = "logged_user"
        private const val USER_ACCOUNT = "user_account"
        private const val NOTIFICATION_ACCOUNT = "notification_account"
    }
}