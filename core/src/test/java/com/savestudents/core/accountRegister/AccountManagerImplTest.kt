package com.savestudents.core.accountRegister

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.auth.FirebaseUser
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.accountManager.model.UserAccount
import com.savestudents.core.sharedPreferences.SharedPreferencesBuilder
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class AccountManagerImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val accountManager: AccountManager = mockk()
    private val sharedPreferences: SharedPreferencesBuilder = mockk(relaxed = true)

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

    @Test
    fun `set notification with true then is notification enabled`() {
        accountManager.setNotifications(true)

        assertTrue { accountManager.isNotificationEnabled() }
    }

    @Test
    fun `set notification with true then is notification not enabled`() {
        accountManager.setNotifications(false)

        assertFalse { accountManager.isNotificationEnabled() }
    }
}