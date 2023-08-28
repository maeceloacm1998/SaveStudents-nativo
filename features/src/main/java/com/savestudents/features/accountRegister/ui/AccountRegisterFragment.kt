package com.savestudents.features.accountRegister.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.savestudents.components.button.disabled
import com.savestudents.features.R
import com.savestudents.core.accountManager.model.UserAccount
import com.savestudents.features.databinding.FragmentAccountRegisterBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AccountRegisterFragment : Fragment(), AccountRegisterContract.View {
    private lateinit var binding: FragmentAccountRegisterBinding

    override val presenter: AccountRegisterContract.Presenter by inject { parametersOf(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountRegisterBinding.inflate(inflater, container, false)
        lifecycleScope.launch {
            presenter.fetchInstitution()
        }
        setupViews()
        return binding.root
    }

    private fun setupViews() {
        binding.run {
            submitButton.setOnClickListener {
                val userAccount = UserAccount(
                    id = "",
                    name = nameTextInput.editTextDefault.text.toString(),
                    email = emailTextInput.editTextDefault.text.toString(),
                    institution = institutionTextInput.editTextAutocomplete.text.toString(),
                    birthDate = dateTextInput.editTextDefault.text.toString()
                )

                lifecycleScope.launch {
                    presenter.validateFields(
                        userAccount,
                        passwordTextLayout.editTextDefault.text.toString()
                    )
                }
            }
        }
    }

    override fun setInstitutionList(institutions: List<String>) {
        binding.institutionTextInput.setAutocompleteItems = institutions
    }

    override fun showEmailFormatError() {
        binding.emailTextInput.error = getString(R.string.account_validation_incorrect_email)
        binding.submitButton.disabled(false)
    }

    override fun showBirthdateFormatError() {
        binding.dateTextInput.error = getString(R.string.account_validation_incorrect_birth_date)
        binding.submitButton.disabled(false)
    }

    override fun showEmptyNameField() {
        binding.nameTextInput.error = getString(R.string.account_validation_empty_name)
        binding.submitButton.disabled(false)
    }

    override fun showEmptyEmailField() {
        binding.emailTextInput.error = getString(R.string.account_validation_empty_email)
        binding.submitButton.disabled(false)
    }

    override fun showEmptyInstitutionField() {
        binding.institutionTextInput.error =
            getString(R.string.account_validation_empty_institution)
        binding.submitButton.disabled(false)
    }

    override fun showEmptyBirthDateField() {
        binding.dateTextInput.error = getString(R.string.account_validation_empty_birth_date)
        binding.submitButton.disabled(false)
    }

    override fun showEmptyPasswordField() {
        binding.passwordTextLayout.error = getString(R.string.account_validation_empty_password)
        binding.submitButton.disabled(false)
    }

    override fun clearFieldsError() {
        binding.emailTextInput.error = ""
        binding.passwordTextLayout.error = ""
        binding.institutionTextInput.error = ""
        binding.nameTextInput.error = ""
        binding.emailTextInput.error = ""
        binding.dateTextInput.error = ""
    }

    override fun goToConfirmationEmail() {
        // TODO implementar proxima tela
    }

    override fun errorRegisterUser(error: Boolean) {
        binding.errorRegisterUser.isVisible = error
        binding.submitButton.disabled(false)
    }

    override fun loading(loading: Boolean) {
        binding.submitButton.disabled(loading)
    }
}