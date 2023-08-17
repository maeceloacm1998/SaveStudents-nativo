package com.savestudents.core.accountManager

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.savestudents.core.firebase.FirebaseConstants
import com.savestudents.core.firebase.FirebaseResponseModel
import com.savestudents.core.firebase.OnFailureModel

class AccountManagerImpl(private val auth: FirebaseAuth) : AccountManager {
    private lateinit var firebaseUser: FirebaseUser

    override fun login(
        email: String,
        password: String,
        firebaseResponseModel: FirebaseResponseModel<FirebaseUser>
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { res ->
                checkNotNull(res.user).run {
                    Log.d("SUCCESS LOGIN", displayName.toString())
                    firebaseUser = this
                    firebaseResponseModel.onSuccess(this)
                }
            }
            .addOnFailureListener { error ->
                Log.e("ERROR LOGIN", error.cause.toString())
                firebaseResponseModel.onFailure(
                    OnFailureModel(
                        code = FirebaseConstants.StatusCode.NOT_FOUND,
                        message = checkNotNull(error.message)
                    )
                )
            }
    }

}