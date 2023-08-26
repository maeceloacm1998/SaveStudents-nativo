package com.savestudents.features.accountRegister.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.savestudents.features.R
import com.savestudents.features.databinding.FragmentAccountRegisterBinding
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
        setupViews()
        return binding.root
    }

    private fun setupViews() {
        binding.run {

        }
    }

    override fun showEmailFormatError() {
        binding.emailTextInput.error = getString(R.string.account_validation_incorrect_email)
    }

    override fun showBirthdateFormatError() {
        binding.dateTextInput.error = getString(R.string.account_validation_incorrect_birth_date)
    }

    override fun showEmptyNameField() {
        binding.nameTextInput.error = getString(R.string.account_validation_empty_name)
    }

    override fun showEmptyEmailField() {
        binding.emailTextInput.error = getString(R.string.account_validation_empty_email)
    }

    override fun showEmptyInstitutionField() {
        binding.institutionTextInput.error =
            getString(R.string.account_validation_empty_institution)
    }

    override fun showEmptyBirthDateField() {
        binding.dateTextInput.error = getString(R.string.account_validation_empty_birth_date)
    }

    override fun showEmptyPasswordField() {
        binding.passwordTextLayout.error = getString(R.string.account_validation_empty_password)
    }
}