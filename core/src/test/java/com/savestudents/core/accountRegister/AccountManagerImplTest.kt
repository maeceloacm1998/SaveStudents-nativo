package com.savestudents.core.accountRegister

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.os.Build
import com.google.firebase.auth.FirebaseUser
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.accountManager.model.UserAccount
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
class AccountManagerImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val accountManager: AccountManager = mockk()

    @Test
    fun `login user when email and password correctly`() = runTest {
        val email = "teste@teste.com"
        val password = "1234"
        val firebaseUser: FirebaseUser = mockk(relaxed = true)

        coEvery { accountManager.login(email, password) } returns Result.success(firebaseUser)
        val res = accountManager.login(email, password)

        assert(res.isSuccess)
    }

    @Test
    fun `login user when email and password is not correctly`() = runTest {
        val email = "teste@teste.com"
        val password = "1234"

        coEvery { accountManager.login(email, password) } returns Result.failure(Throwable())
        val res = accountManager.login(email, password)

        assert(res.isFailure)
    }

    @Test
    fun `register user when all information correctly`() = runTest {
        val userAccount: UserAccount = mockk(relaxed = true)
        val firebaseUser: FirebaseUser = mockk(relaxed = true)

        coEvery { accountManager.register(userAccount) } returns Result.success(firebaseUser)
        val res = accountManager.register(userAccount)

        assert(res.isSuccess)
    }

    @Test
    fun `register user when all information is not correctly`() = runTest {
        val userAccount: UserAccount = mockk(relaxed = true)

        coEvery { accountManager.register(userAccount) } returns Result.failure(Throwable())
        val res = accountManager.register(userAccount)

        assert(res.isFailure)
    }
}