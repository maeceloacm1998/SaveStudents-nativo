package com.savestudents.features.addMatter

import android.os.Bundle
import android.view.View
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import com.savestudents.components.textInput.TextInputCustomView
import com.savestudents.core.utils.BaseFragment
import com.savestudents.features.NavigationActivity
import com.savestudents.features.R
import com.savestudents.features.databinding.FragmentAddMatterBinding

class AddMatterFragment :
    BaseFragment<FragmentAddMatterBinding, NavigationActivity>(FragmentAddMatterBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity?.handleTitleToolbar("Adicionar Matéria")

        setupViews()
    }

    private fun setupViews() {
        binding.initialHourTextLayout.onClickInputNotKeyboard {
            val picker = handleTimePicker(getString(R.string.add_matter_select_initial_hour))
            picker.show(parentFragmentManager, "")

            picker.run {
                addOnPositiveButtonClickListener {
                    setTimeInEditText(binding.initialHourTextLayout, hour, minute)
                }
            }
        }

        binding.finalHourTextLayout.onClickInputNotKeyboard {
            val picker = handleTimePicker(getString(R.string.add_matter_select_final_hour))
            picker.show(parentFragmentManager, "")

            picker.run {
                addOnPositiveButtonClickListener {
                    setTimeInEditText(binding.finalHourTextLayout, hour, minute)
                }
            }
        }
    }

    private fun setTimeInEditText(editable: TextInputCustomView, hour: Int, minutes: Int) {
        editable.editTextDefault.setText("$hour:$minutes")
    }

    private fun handleTimePicker(title: String): MaterialTimePicker {
        return MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(8)
            .setMinute(0)
            .setInputMode(INPUT_MODE_CLOCK)
            .setTitleText(title)
            .build()
    }
}