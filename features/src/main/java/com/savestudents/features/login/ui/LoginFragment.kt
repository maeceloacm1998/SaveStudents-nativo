package com.savestudents.features.login.ui

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.savestudents.components.button.disabled
import com.savestudents.core.utils.BaseFragment
import com.savestudents.features.NavigationActivity
import com.savestudents.features.R
import com.savestudents.features.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class LoginFragment :
    BaseFragment<FragmentLoginBinding, NavigationActivity>(FragmentLoginBinding::inflate),
    LoginContract.View {

    override val presenter: LoginContract.Presenter by inject { parametersOf(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity?.visibleToolbar(false)
        setupViews()
    }

    private fun setupViews() {
        binding.apply {
            emailTextLayout.editTextDefault.addTextChangedListener {
                hideValidateAccountError()
            }

            passwordTextLayout.editTextDefault.addTextChangedListener {
                hideValidateAccountError()
            }

            registerButton.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_accountRegisterFragment)
            }

            submitButton.setOnClickListener {
                binding.run {
                    val email = emailTextLayout.editTextDefault.text.toString()
                    val password = passwordTextLayout.editTextDefault.text.toString()

                    lifecycleScope.launch {
                        presenter.validateAccount(email, password)
                    }
                }
            }
        }
    }

    override fun loadingValidateAccount(loading: Boolean) {
        binding.submitButton.disabled(loading)
    }

    override fun showEmptyEmailError() {
        errorFields(
            emailMessage = getString(R.string.account_validation_empty_email)
        )
    }

    override fun showEmptyPasswordError() {
        errorFields(
            passwordMessage = getString(R.string.account_validation_empty_password)
        )
    }

    override fun showIncorrectEmailError() {
        errorFields(
            emailMessage = getString(R.string.account_validation_incorrect_email)
        )
    }


    override fun showValidateAccountError() {
        errorFields(
            emailMessage = " ",
            passwordMessage = getString(R.string.account_validation_error)
        )
    }

    override fun hideValidateAccountError() {
        errorFields(
            emailMessage = getString(R.string.not_text_error),
            passwordMessage = getString(R.string.not_text_error)
        )
    }

    override fun successValidateAccount() {
        findNavController().run {
            navigate(R.id.action_loginFragment_to_homeFragment)
            clearBackStack(R.id.action_loginFragment_to_homeFragment)
        }
    }

    private fun errorFields(emailMessage: String? = "", passwordMessage: String? = "") {
        binding.apply {
            emailTextLayout.error = checkNotNull(emailMessage)
            passwordTextLayout.error = checkNotNull(passwordMessage)
        }
    }
}