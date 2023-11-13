package com.savestudents.features.notificationconfig.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.savestudents.core.utils.BaseFragment
import com.savestudents.features.NavigationActivity
import com.savestudents.features.databinding.FragmentNotificationConfigBinding
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class NotificationConfigFragment :
    BaseFragment<FragmentNotificationConfigBinding, NavigationActivity>(
        FragmentNotificationConfigBinding::inflate
    ),
    NotificationConfigContract.View {

    override val presenter: NotificationConfigContract.Presenter by inject { parametersOf(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity?.handleTitleToolbar("Notificações")
        parentActivity?.goBackPressed {
            findNavController().popBackStack()
        }
        setupViews()
    }

    override fun onResume() {
        super.onResume()
        presenter.checkNotificationResult()
    }

    private fun setupViews() {
        binding.apply {
            swNotification.setOnClickListener {
                presenter.handleNotification()
            }
        }
    }

    override fun enabledNotificationSwitch() {
        binding.swNotification.isChecked = true
    }

    override fun disabledNotificationSwitch() {
        binding.swNotification.isChecked = false
    }
}