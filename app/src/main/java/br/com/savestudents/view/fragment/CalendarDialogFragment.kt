package br.com.savestudents.view.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import br.com.savestudents.R
import br.com.savestudents.databinding.FragmentCalendarDialogBinding
import java.util.*

class CalendarDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentCalendarDialogBinding
    private var selectedDayChange: Long = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarDialogBinding.inflate(inflater, container, false)
        handleSetDate()
        handleSelectedDate()
        handleCancelButton()
        handleSelectedDayChange()
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.CalendarModalDialog)
    }

    private fun handleSetDate() {
        if (selectedDateTimestamp != null) {
            binding.calendar.setDate(selectedDateTimestamp as Long, false, true)
        }
    }

    private fun handleSelectedDayChange() {
        binding.calendar.setOnDateChangeListener { calendarView, year, month, day ->
            val instance = Calendar.getInstance()
            instance.set(year, month, day)
            selectedDayChange = instance.timeInMillis
        }
    }

    private fun handleSelectedDate() {
        binding.buttonSubmit.setOnClickListener {
            //TODO submit
        }
    }

    private fun handleCancelButton() {
        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val TAG = "CalendarDialogModal"
        private const val SELECTED_DATE_TIMESTAMP = "selected_date"
        private var selectedDateTimestamp: Long? = null

        fun saveBundle(selectedDate: Long) {
            selectedDateTimestamp = selectedDate
        }
    }
}