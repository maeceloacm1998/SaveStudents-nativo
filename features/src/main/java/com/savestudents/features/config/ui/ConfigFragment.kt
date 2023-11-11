package com.savestudents.features.config.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.savestudents.core.utils.BaseFragment
import com.savestudents.features.NavigationActivity
import com.savestudents.features.databinding.FragmentConfigBinding
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ConfigFragment :
    BaseFragment<FragmentConfigBinding, NavigationActivity>(FragmentConfigBinding::inflate),
    ConfigContract.View {

    override val presenter: ConfigContract.Presenter by inject {
        parametersOf(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity?.handleTitleToolbar("Configuração")
        parentActivity?.goBackPressed {
            findNavController().popBackStack()
        }
    }

}