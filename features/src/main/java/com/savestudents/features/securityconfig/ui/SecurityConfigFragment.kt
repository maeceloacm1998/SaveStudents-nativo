package com.savestudents.features.securityconfig.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.savestudents.core.utils.BaseFragment
import com.savestudents.features.NavigationActivity
import com.savestudents.features.databinding.FragmentSecurityConfigBinding
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SecurityConfigFragment :
    BaseFragment<FragmentSecurityConfigBinding, NavigationActivity>(FragmentSecurityConfigBinding::inflate),
    SecurityConfigContract.View {

    override val presenter: SecurityConfigContract.Presenter by inject { parametersOf(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity?.handleTitleToolbar("Acesso e segurança")
        parentActivity?.goBackPressed {
            findNavController().popBackStack()
        }

        presenter.fetchUser()
    }

    override fun handleEmail(email: String) {
        binding.tiEmail.editTextDefault.apply {
            isEnabled = false
            setText(email)
        }
    }

    override fun handleName(name: String) {
        binding.tiName.editTextDefault.apply {
            isEnabled = false
            setText(name)
        }
    }

    override fun handleDate(date: String) {
        binding.tiDate.editTextDefault.apply {
            isEnabled = false
            setText(date)
        }
    }
}