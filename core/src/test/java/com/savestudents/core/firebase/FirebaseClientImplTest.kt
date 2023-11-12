package com.savestudents.core.firebase

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class FirebaseClientImplTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val database: FirebaseFirestore = mockk()
    private val firebaseClient: FirebaseClient = FirebaseClientImpl(database)

    @Test
    fun `when called getDocument in firebase, return success document`() = runTest {
        val request =  database.collection("teste").get()

        coEvery { request.isSuccessful } returns true
        val res = firebaseClient.getDocumentValue("teste")

        assert(res.isSuccess)
    }

    @Test
    fun `when called getDocument in firebase, return failure document`() = runTest {
        coEvery { firebaseClient.getDocumentValue("teste") } returns Result.failure(Throwable())
        val res = firebaseClient.getDocumentValue("teste")

        assert(res.isFailure)
    }
}