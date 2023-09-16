package com.savestudents.features.accountRegister

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.os.Build
import com.google.firebase.auth.FirebaseUser
import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.accountManager.model.UserAccount
import com.savestudents.core.firebase.FirebaseClient
import com.savestudents.features.accountRegister.ui.AccountRegisterContract
import com.savestudents.features.accountRegister.ui.AccountRegisterPresenter
import io.mockk.MockKAnnotations
import io.mockk.mockk
import org.mockito.Mockito.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class AccountRegisterPresenterTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var view: AccountRegisterContract.View

    @Mock
    private lateinit var accountManager: AccountManager

    @Mock
    private lateinit var firebaseClient: FirebaseClient

    @Spy
    private lateinit var presenter: AccountRegisterContract.Presenter

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        MockitoAnnotations.openMocks(this)

        presenter = AccountRegisterPresenter(view, accountManager, firebaseClient)
    }

    @Test
    fun `show error empty name when not insert name in field`() = runTest {
        val user = UserAccount(
            id = "",
            name = "",
            email = "",
            institution = "",
            birthDate = "",
            password = ""
        )
        presenter.validateFields(user)
        verify(view, Mockito.times(1)).showEmptyNameField()
    }

    @Test
    fun `show error empty email when not insert email in field`() = runTest {
        val user = UserAccount(
            id = "",
            name = "teste",
            email = "",
            institution = "",
            birthDate = "",
            password = ""
        )
        presenter.validateFields(user)
        verify(view, Mockito.times(1)).showEmptyEmailField()
    }

    @Test
    fun `show error format email when insert email in field`() = runTest {
        val user = UserAccount(
            id = "",
            name = "teste",
            email = "teste",
            institution = "",
            birthDate = "",
            password = ""
        )
        presenter.validateFields(user)
        verify(view, Mockito.times(1)).showEmailFormatError()
    }

    @Test
    fun `show error empty birthDate when not insert birthdate in field`() = runTest {
        val user = UserAccount(
            id = "",
            name = "teste",
            email = "teste@teste.com",
            institution = "PUCMINAS - São Gabriel",
            birthDate = "",
            password = ""
        )
        presenter.validateFields(user)
        verify(view, Mockito.times(1)).showEmptyBirthDateField()
    }

    @Test
    fun `show error empty institution when not insert institution in field`() = runTest {
        val user = UserAccount(
            id = "",
            name = "teste",
            email = "teste@teste.com",
            institution = "",
            birthDate = "",
            password = ""
        )
        presenter.validateFields(user)
        verify(view, Mockito.times(1)).showEmptyBirthDateField()
    }

    @Test
    fun `show error empty password when not insert password in field`() = runTest {
        val user = UserAccount(
            id = "",
            name = "teste",
            email = "teste@teste.com",
            institution = "PUCMINAS - São Gabriel",
            birthDate = "02/02/02",
            password = ""
        )
        presenter.validateFields(user)
        verify(view, Mockito.times(1)).showEmptyPasswordField()
    }

    @Test
    fun `success with email and password correctly`() = runTest {
        val user = UserAccount(
            id = "",
            name = "teste",
            email = "teste@teste.com",
            institution = "PUCMINAS - São Gabriel",
            birthDate = "02/02/02",
            password = "123456"
        )
        val firebaseUser: FirebaseUser = mockk(relaxed = true)
        Mockito.`when`(accountManager.register(user))
            .thenReturn(Result.success(firebaseUser))

        presenter.validateFields(user)

        verify(view, Mockito.times(1)).goToHomeFragment()
    }

    @Test
    fun `error with email and password incorrectly`() = runTest {
        val user = UserAccount(
            id = "",
            name = "teste",
            email = "teste@teste.com",
            institution = "PUCMINAS - São Gabriel",
            birthDate = "02/02/02",
            password = "123456"
        )
        val error = mock(Throwable::class.java)
        Mockito.`when`(accountManager.register(user))
            .thenReturn(Result.failure(error))

        presenter.validateFields(user)

        verify(view, Mockito.times(1)).errorRegisterUser(true)
    }
}