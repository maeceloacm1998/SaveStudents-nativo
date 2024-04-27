package com.savestudents.features.addEvent.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.savestudents.components.button.disabled
import com.savestudents.components.snackbar.SnackBarCustomType
import com.savestudents.core.utils.BaseFragment
import com.savestudents.features.NavigationActivity
import com.savestudents.features.R
import com.savestudents.features.databinding.FragmentEventBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class EventFragment() :
    BaseFragment<FragmentEventBinding, NavigationActivity>(FragmentEventBinding::inflate),
    EventContract.View {

    override val presenter: EventContract.Presenter by inject { parametersOf(this) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity?.handleTitleToolbar("Adicionar Evento")
        parentActivity?.goBackPressed {
            findNavController().popBackStack()
        }

        setupViews()
    }

    private fun setupViews() {
        binding.submitButton.setOnClickListener {
            val eventName = binding.tiEvent.editTextDefault.text.toString()
            val date = binding.datePicker.timestemp
            lifecycleScope.launch {
                presenter.validateEvent(eventName, date)
            }
        }
    }

    override fun loading(loading: Boolean) {
        binding.submitButton.disabled(loading)
    }

    override fun showEventNameError() {
        binding.tiEvent.error = getString(R.string.add_event_empty_event_error)
    }

    override fun hideEventNameError() {
        binding.tiEvent.error = ""
    }

    override fun showDateSelectedError() {
        binding.datePicker.error = getString(R.string.add_event_not_selected_date_error)
    }

    override fun hideDateSelectedError() {
        binding.datePicker.error = ""
    }

    override fun showSnackBarSuccess(eventName: String) {
        parentActivity?.showSnackBar(
            getString(
                R.string.add_event_success_register_alert,
                eventName
            ), SnackBarCustomType.SUCCESS
        )
    }

    override fun showSnackBarError(eventName: String) {
        parentActivity?.showSnackBar(
            getString(
                R.string.add_event_success_register_alert,
                eventName
            ), SnackBarCustomType.ERROR
        )
    }

    override fun goToCurriculum() {
        findNavController().popBackStack()
    }
}