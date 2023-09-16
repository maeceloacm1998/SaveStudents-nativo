package com.savestudents.features.accountRegister.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.savestudents.components.button.disabled
import com.savestudents.features.R
import com.savestudents.core.accountManager.model.UserAccount
import com.savestudents.core.utils.BaseFragment
import com.savestudents.features.NavigationActivity
import com.savestudents.features.databinding.FragmentAccountRegisterBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AccountRegisterFragment : BaseFragment<FragmentAccountRegisterBinding, NavigationActivity>(
    FragmentAccountRegisterBinding::inflate), AccountRegisterContract.View {

    override val presenter: AccountRegisterContract.Presenter by inject { parametersOf(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity?.handleTitleToolbar(getString(R.string.register_title))
        lifecycleScope.launch {
            presenter.fetchInstitution()
        }
        setupViews()
    }

    private fun setupViews() {
        binding.run {
            submitButton.setOnClickListener {
                val userAccount = UserAccount(
                    id = "",
                    name = nameTextInput.editTextDefault.text.toString(),
                    email = emailTextInput.editTextDefault.text.toString(),
                    institution = institutionTextInput.editTextAutocomplete.text.toString(),
                    birthDate = dateTextInput.editTextDefault.text.toString(),
                    password = passwordTextLayout.editTextDefault.text.toString()
                )

                lifecycleScope.launch { presenter.validateFields(userAccount) }
            }

            parentActivity?.goBackPressed {
                findNavController().navigateUp()
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

    override fun goToHomeFragment() {
        findNavController().run {
            navigate(R.id.action_accountRegisterFragment_to_homeFragment)
            backQueue.clear()
        }
    }

    override fun errorRegisterUser(error: Boolean) {
        binding.errorRegisterUser.isVisible = error
        binding.submitButton.disabled(false)
    }

    override fun loading(loading: Boolean) {
        binding.submitButton.disabled(loading)
    }
}