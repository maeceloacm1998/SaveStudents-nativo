package com.savestudents.features.login

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.os.Build
import com.google.firebase.auth.FirebaseUser
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.features.login.ui.LoginContract
import com.savestudents.features.login.ui.LoginPresenter
import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class LoginPresenterTest : KoinTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var view: LoginContract.View

    @Mock
    private lateinit var accountManager: AccountManager

    @Spy
    private lateinit var presenter: LoginContract.Presenter

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        MockitoAnnotations.openMocks(this)

        presenter = LoginPresenter(view, accountManager)
    }

    @Test
    fun `call showEmptyEmailError function when user not digit email`() = runTest {
        presenter.validateAccount("", "123456")
        verify(view, times(1)).showEmptyEmailError()
    }

    @Test
    fun `call showEmptyPasswordError function when user not digit password`() = runTest {
        presenter.validateAccount("teste@teste.com", "")
        verify(view, times(1)).showEmptyPasswordError()
    }

    @Test
    fun `call showIncorrectEmail function when get incorrect format in email`() = runTest {
        val email = "teste"
        val password = "123456"
        presenter.validateAccount(email, password)
        verify(view, times(1)).showIncorrectEmailError()
    }

    @Test
    fun `call successValidateAccount() function when get correctly email and password`() = runTest {
        val email = "teste@teste.com"
        val password = "123456"
        val userMock = mock(FirebaseUser::class.java)

        `when`(accountManager.login(email,password)).thenReturn(Result.success(userMock))
        presenter.validateAccount(email, password)
        verify(view, times(1)).successValidateAccount()
    }

    @Test
    fun `call showValidateAccountError() function when get incorrect email and password`() = runTest {
        val email = "teste@teste.com"
        val password = "123456"

        `when`(accountManager.login(email,password)).thenReturn(Result.failure(Throwable()))
        presenter.validateAccount(email, password)
        verify(view, times(1)).showValidateAccountError()
    }
}