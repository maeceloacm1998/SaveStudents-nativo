package com.savestudents.features.addMatter.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import com.savestudents.components.button.disabled
import com.savestudents.components.snackbar.SnackBarCustomType
import com.savestudents.components.textInput.TextInputCustomView
import com.savestudents.core.utils.BaseFragment
import com.savestudents.core.utils.DateUtils
import com.savestudents.features.NavigationActivity
import com.savestudents.features.R
import com.savestudents.features.addMatter.models.EventType
import com.savestudents.features.addMatter.models.Matter
import com.savestudents.features.databinding.BottomSheetAddMatterOptionBinding
import com.savestudents.features.databinding.FragmentAddMatterBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AddMatterFragment :
    BaseFragment<FragmentAddMatterBinding, NavigationActivity>(FragmentAddMatterBinding::inflate),
    AddMatterContract.View {

    override val presenter: AddMatterContract.Presenter by inject { parametersOf(this) }

    private var bottomSheetBinding: BottomSheetAddMatterOptionBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity?.handleTitleToolbar("Adicionar Matéria")
        parentActivity?.goBackPressed {
            findNavController().popBackStack()
        }

        lifecycleScope.launch {
            presenter.start()
            presenter.handleFetchMatters()
        }
    }

    override fun onSetupViewsTextLayout() {
        binding.run {
            initialHourTextLayout.editTextDefault.hint = getString(R.string.add_matter_initial_hour)
            initialHourTextLayout.onClickInputNotKeyboard {
                val picker = handleTimePicker(
                    getString(R.string.add_matter_select_initial_hour),
                    presenter.onGetInitialHour(),
                    presenter.onGetInitialMinutes()
                )
                picker.show(parentFragmentManager, "")

                picker.run {
                    addOnPositiveButtonClickListener {
                        val time = DateUtils.formatTime(hour.toString(), minute.toString())
                        presenter.onSaveInitialHourSelected(time)
                        setTimeInEditText(binding.initialHourTextLayout, time)
                    }
                }
            }
        }
    }

    override fun onSetupViewsAddMatter() {
        binding.btAddMatterOption.setOnClickListener {
            bottomSheetBinding = parentActivity?.showBottomSheet(
                BottomSheetAddMatterOptionBinding.inflate(layoutInflater)
            )

            bottomSheetBinding?.tiPeriod?.showKeyboard = false

            bottomSheetBinding?.submitButton?.setOnClickListener {
                val matterName = bottomSheetBinding?.tiMatter?.editTextDefault?.text.toString()
                val period = bottomSheetBinding?.tiPeriod?.editTextAutocomplete?.text.toString()
                lifecycleScope.launch {
                    presenter.handleValidateAddMatterOption(
                        matterName = matterName,
                        period = period
                    )
                }
            }
        }
    }

    override fun onSetupViewsCreateMatter() {
        binding.apply {
            submitButton.setOnClickListener {
                val daysSelected: List<String> = chipGroup.children
                    .toList()
                    .filter { (it as Chip).isChecked }
                    .map { (it as Chip).text.toString() }

                onLoadingRegister(true)
                lifecycleScope.launch {
                    presenter.handleValidateMatter(daysSelected)
                }
            }
        }
    }

    override fun onSetupViewsMatterInput() {
        binding.matterTextInput.onItemSelected { item ->
            presenter.onMatterSelect(item)
        }
    }

    private fun setTimeInEditText(editable: TextInputCustomView, time: String) {
        editable.editTextDefault.hint = time
    }

    private fun handleTimePicker(title: String, hour: Int, minute: Int): MaterialTimePicker {
        return MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(hour)
            .setMinute(minute)
            .setInputMode(INPUT_MODE_CLOCK)
            .setTitleText(title)
            .build()
    }

    override fun onLoading(loading: Boolean) {
        binding.run {
            if (loading) {
                this.loading.root.isVisible = true
                this.error.root.isVisible = false
                this.layout.isVisible = false

            } else {
                this.loading.root.isVisible = false
                this.error.root.isVisible = false
                this.layout.isVisible = true
            }
        }
    }

    override fun onLoadingRegister(loading: Boolean) {
        binding.submitButton.disabled(loading)
    }

    override fun showError() {
        binding.error.root.isVisible = true
    }

    override fun showErrorMatterNotSelected(visibility: Boolean) {
        binding.run {
            if (visibility) {
                binding.matterTextInput.error = getString(R.string.add_matter_select_matter_error)
            } else {
                binding.matterTextInput.error = ""
            }
        }
    }

    override fun showErrorDaysNotSelected(visibility: Boolean) {
        binding.selectDaysError.run {
            isVisible = visibility
            text = getString(R.string.add_matter_select_days_error)
        }
    }

    override fun showErrorInitialHourNotSelected(visibility: Boolean) {
        binding.run {
            if (visibility) {
                binding.initialHourTextLayout.error =
                    getString(R.string.add_matter_select_days_error)
            } else {
                binding.initialHourTextLayout.error = ""
            }
        }
    }

    override fun showErrorAddMatterOptionMatterNameNotSelected(visibility: Boolean) {
        if (visibility) {
            bottomSheetBinding?.tiMatter?.error =
                getString(R.string.bottom_sheet_add_matter_option_matter_error)
        } else {
            bottomSheetBinding?.tiMatter?.error = ""
        }
    }

    override fun showErrorAddMatterOptionPeriodNotSelected(visibility: Boolean) {
        if (visibility) {
            bottomSheetBinding?.tiPeriod?.error =
                getString(R.string.bottom_sheet_add_matter_option_period_error)
        } else {
            bottomSheetBinding?.tiPeriod?.error = ""
        }
    }

    override fun showSnackbarStatus(matterName: String?, snackBarCustomType: SnackBarCustomType) {
        when (snackBarCustomType) {
            SnackBarCustomType.SUCCESS -> {
                parentActivity?.showSnackBar(
                    getString(
                        R.string.add_matter_success_register_alert,
                        matterName
                    ), snackBarCustomType
                )
            }

            else -> {
                parentActivity?.showSnackBar(
                    getString(R.string.add_matter_error_register_alert),
                    snackBarCustomType
                )
            }
        }
    }

    override fun showSnackbarAddMatterOption(
        message: String,
        snackBarCustomType: SnackBarCustomType
    ) {
        lifecycleScope.launch {
            presenter.handleFetchMatters()
        }

        parentActivity?.apply {
            hideBottomSheet()
            showSnackBar(message, snackBarCustomType)
        }
    }

    override fun goToCurriculum() {
        findNavController().popBackStack()
    }

    override fun onSetMatterOptions(matterList: List<String>) {
        binding.matterTextInput.setAutocompleteItems = matterList
    }

    override fun onMatterSelect(matter: Matter) {
        binding.matterInformation.root.isVisible = true
        binding.matterInformation.run {
            clDate.isVisible = false
            chType.text = EventType.MATTER.value
            tvEventTitle.text = matter.matterName
            tvEventPeriod.text = matter.period
            btDelete.isVisible = false
        }
    }
}