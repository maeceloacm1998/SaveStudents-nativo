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
import com.savestudents.components.textInput.TextInputCustomView
import com.savestudents.core.utils.BaseFragment
import com.savestudents.core.utils.DateUtils
import com.savestudents.features.NavigationActivity
import com.savestudents.features.R
import com.savestudents.features.addMatter.models.Matter
import com.savestudents.features.databinding.FragmentAddMatterBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AddMatterFragment :
    BaseFragment<FragmentAddMatterBinding, NavigationActivity>(FragmentAddMatterBinding::inflate),
    AddMatterContract.View {

    override val presenter: AddMatterContract.Presenter by inject { parametersOf(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity?.handleTitleToolbar("Adicionar Matéria")
        parentActivity?.goBackPressed {
            findNavController().popBackStack()
        }
        lifecycleScope.launch {
            presenter.fetchMatters()
        }
        setupViews()
    }

    private fun setupViews() {
        binding.run {
            initialHourTextLayout.editTextDefault.hint = getString(R.string.add_matter_initial_hour)
            finalHourTextLayout.editTextDefault.hint = getString(R.string.add_matter_final_hour)

            initialHourTextLayout.onClickInputNotKeyboard {
                val picker = handleTimePicker(
                    getString(R.string.add_matter_select_initial_hour),
                    presenter.getInitialHour(),
                    presenter.getInitialMinutes()
                )
                picker.show(parentFragmentManager, "")

                picker.run {
                    addOnPositiveButtonClickListener {
                        val time: String =
                            DateUtils.formatTime(this.hour.toString(), this.minute.toString())
                        presenter.saveInitialHourSelected(time)
                        setTimeInEditText(binding.initialHourTextLayout, time)
                    }
                }
            }

            finalHourTextLayout.onClickInputNotKeyboard {
                val picker = handleTimePicker(
                    getString(R.string.add_matter_select_final_hour),
                    presenter.getFinalHour(),
                    presenter.getFinalMinutes()
                )
                picker.show(parentFragmentManager, "")

                picker.run {
                    addOnPositiveButtonClickListener {
                        val time: String =
                            DateUtils.formatTime(this.hour.toString(), this.minute.toString())
                        presenter.saveFinalHourSelected(time)
                        setTimeInEditText(binding.finalHourTextLayout, time)
                    }
                }
            }

            matterTextInput.onItemSelected { item ->
                presenter.matterSelect(item)
            }

            submitButton.setOnClickListener {
                val daysSelected: List<String> = chipGroup.children
                    .toList()
                    .filter { (it as Chip).isChecked }
                    .map { (it as Chip).text.toString() }

                loadingRegister(true)
                lifecycleScope.launch {
                    presenter.registerMatter(daysSelected)
                }
            }
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

    override fun loading(loading: Boolean) {
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

    override fun loadingRegister(loading: Boolean) {
        binding.submitButton.disabled(loading)
    }

    override fun showError() {
        binding.error.root.isVisible = true
    }

    override fun errorMatterNotSelected(visibility: Boolean) {
        binding.run {
            if (visibility) {
                binding.matterTextInput.error = getString(R.string.add_matter_select_matter_error)
            } else {
                binding.matterTextInput.error = ""
            }
        }
    }

    override fun errorDaysNotSelected(visibility: Boolean) {
        binding.selectDaysError.run {
            isVisible = visibility
            text = getString(R.string.add_matter_select_days_error)
        }
    }

    override fun errorInitialHourNotSelected(visibility: Boolean) {
        binding.run {
            if (visibility) {
                binding.initialHourTextLayout.error =
                    getString(R.string.add_matter_select_days_error)
            } else {
                binding.initialHourTextLayout.error = ""
            }
        }
    }

    override fun errorFinalHourNotSelected(visibility: Boolean) {
        binding.run {
            if (visibility) {
                binding.finalHourTextLayout.error = getString(R.string.add_matter_select_days_error)
            } else {
                binding.finalHourTextLayout.error = ""
            }
        }
    }

    override fun setMatterOptions(matterList: List<String>) {
        binding.matterTextInput.setAutocompleteItems = matterList
    }

    override fun handleMatterSelect(matter: Matter) {
        binding.run {
            matterInformation.isVisible = true
            matterName.text = matter.matterName
            period.text = getString(R.string.add_matter_period, matter.period)
            teacher.text = getString(R.string.add_matter_teacher, matter.teacherName)
        }
    }
}