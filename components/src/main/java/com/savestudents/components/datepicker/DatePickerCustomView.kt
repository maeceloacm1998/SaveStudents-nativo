package com.savestudents.components.datepicker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.datepicker.MaterialDatePicker
import com.savestudents.components.R
import com.savestudents.components.databinding.DatePickerCustomViewBinding
import com.savestudents.core.utils.DateUtils
import com.savestudents.core.utils.DateUtils.formatDateWithPattern


class DatePickerCustomView(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {
    private val binding: DatePickerCustomViewBinding = DatePickerCustomViewBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    var timestemp: Long? = null

    var date: Long = 0L
        set(value) {
            binding.tvDate.text = if (value != 0L) {
                timestemp = value
                formatDateWithPattern(DateUtils.DATE_WITH_MONTH_NAME, value)
            } else {
                binding.tvDate.context.getString(R.string.date_picker_custom_view_empty_selected_date)
            }
            field = value
        }

    var error: String = ""
        set(value) {
            binding.apply {
                if (value.isNotEmpty()) {
                    selectDateError.text = value
                    container.isVisible = true
                    container.background =
                        ContextCompat.getDrawable(container.context, R.drawable.bg_border_error)
                } else {
                    container.isVisible = true
                    container.background =
                        ContextCompat.getDrawable(container.context, R.drawable.bg_border_gray)
                }
            }
            field = value
        }

    init {
        setupViews()
    }

    private fun setupViews() {
        binding.container.setOnClickListener {
            handleDatePicker()
        }
    }

    private fun handleDatePicker() {
        val fragmentManager = (context as AppCompatActivity).supportFragmentManager

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(binding.container.context.getString(R.string.date_picker_custom_view_empty_selected_date))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val dateSelected = DateUtils.addOneDayToTimestamp(selection)
            timestemp = dateSelected
            binding.tvDate.text =
                formatDateWithPattern(DateUtils.DATE_WITH_MONTH_NAME, dateSelected)
        }

        datePicker.show(fragmentManager, "date_picker")
    }
}