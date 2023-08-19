package com.savestudents.features.login.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.savestudents.components.button.disabled
import com.savestudents.features.R
import com.savestudents.features.databinding.FragmentLoginBinding
import com.savestudents.features.login.model.LoginContract
import org.koin.android.ext.android.inject

class LoginFragment : Fragment(), LoginContract.View {
    private lateinit var binding: FragmentLoginBinding
    override val presenter by inject<LoginContract.Presenter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        setupViews()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        presenter.view = this
    }

    private fun setupViews() {
        binding.apply {
            emailEditText.addTextChangedListener {
                hideValidateAccountError()
            }

            passwordEditText.addTextChangedListener {
                hideValidateAccountError()
            }

            registerButton.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_accountRegisterFragment)
            }

            submitButton.setOnClickListener {
                binding.run {
                    val email = emailTextLayout.editText?.text.toString()
                    val password = passwordTextLayout.editText?.text.toString()

                    presenter.validateAccount(email, password)
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
        // TODO colocar a tela que vamos redirecionar apos o login com sucesso
    }

    private fun errorFields(emailMessage: String? = "", passwordMessage: String? = "") {
        binding.apply {
            emailTextLayout.error = emailMessage
            passwordTextLayout.error = passwordMessage
        }
    }
}