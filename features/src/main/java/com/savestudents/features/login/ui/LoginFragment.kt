package com.savestudents.features.login.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialSharedAxis
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
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_accountRegisterFragment)
        }
        binding.submitButton.setOnClickListener {
            presenter.validateAccount()
        }
    }

    override fun loadingValidateAccount(loading: Boolean) {
        // TODO criar um componente de botao e colocar dentro
    }


    override fun showValidateAccountError() {
        binding.apply {
            emailTextLayout.error = getString(R.string.not_text_error)
            passwordTextLayout.error = getString(R.string.account_validation_error)
        }
    }

    override fun successValidateAccount() {
        // TODO colocar a tela que vamos redirecionar apos o login com sucesso
    }
}