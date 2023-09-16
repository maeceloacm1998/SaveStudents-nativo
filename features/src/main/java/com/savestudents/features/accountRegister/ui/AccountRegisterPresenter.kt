package com.savestudents.features.accountRegister.ui

import com.savestudents.core.accountManager.AccountManager
import com.savestudents.core.firebase.FirebaseClient
import com.savestudents.core.accountManager.model.UserAccount
import com.savestudents.features.accountRegister.model.Institution

class AccountRegisterPresenter(
    private val view: AccountRegisterContract.View,
    private val accountManager: AccountManager,
    private val firebaseClient: FirebaseClient,
) : AccountRegisterContract.Presenter {
    override fun start() {}

    override suspend fun fetchInstitution() {
        firebaseClient.getDocumentValue("institutions").onSuccess { document ->
            val institutions =
                document.map { requireNotNull(it.toObject(Institution::class.java)).institution }
            view.setInstitutionList(institutions)
        }
    }

    override suspend fun validateFields(user: UserAccount) {
        view.loading(true)
        view.errorRegisterUser(false)
        when {
            user.name.isEmpty() -> view.showEmptyNameField()
            user.email.isEmpty() -> view.showEmptyEmailField()
            !isValidEmail(user.email) -> view.showEmailFormatError()
            user.birthDate.isEmpty() -> view.showEmptyBirthDateField()
            user.institution.isEmpty() -> view.showEmptyInstitutionField()
            user.password.isEmpty() -> view.showEmptyPasswordField()
            else -> {
                view.clearFieldsError()
                saveData(user)
            }
        }
    }

    override suspend fun saveData(user: UserAccount) {
        accountManager.register(user).onSuccess {
            view.goToHomeFragment()
        }.onFailure {
            view.errorRegisterUser(true)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}")
        return emailPattern.matches(email)
    }
}