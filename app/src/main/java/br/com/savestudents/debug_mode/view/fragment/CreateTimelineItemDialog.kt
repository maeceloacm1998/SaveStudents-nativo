package br.com.savestudents.debug_mode.view.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import br.com.savestudents.R
import br.com.savestudents.databinding.DialogCreateTimelineItemBinding
import br.com.savestudents.model.contract.CreateTimelineItemDialogContract
import br.com.savestudents.service.internal.dao.CreateTimelineDAO
import br.com.savestudents.service.internal.database.CreateTimelineItemsDB
import br.com.savestudents.service.internal.entity.CreateTimelineItemEntity
import java.text.SimpleDateFormat
import java.util.*

class CreateTimelineItemDialog(private val mContract: CreateTimelineItemDialogContract) : DialogFragment() {
    private lateinit var binding: DialogCreateTimelineItemBinding
    private lateinit var timelineItemDAO: CreateTimelineDAO
    private var selectedDate: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCreateTimelineItemBinding.inflate(inflater, container, false)
        timelineItemDAO = CreateTimelineItemsDB.getDataBase(requireContext()).createTimelineDAO()

        setDefaultStyleSelectDateButton()
        handleSelectDateButton()
        handleSelectDateInCalendar()
        handleSubmitButton()
        handleCloseDialog()
        closeSelectDateCalendar()
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.Theme_Dialog)
    }

    private fun handleSelectDateButton() {
        binding.selectDateButton.setOnClickListener {
            showSelectDateCalendar()
        }
    }

    private fun handleSelectDateInCalendar() {
        binding.calendar.setOnDateChangeListener { calendarView, year, month, day ->
            val instance = Calendar.getInstance()
            instance.set(year, month, day)
            selectedDate = instance.timeInMillis
            handleSelectDateTextButton(instance.timeInMillis)
        }
    }

    private fun handleSelectDateTextButton(timestamp: Long) {
        setStyledSelectedDateButton()
        closeSelectDateCalendar()
        val pattern = SimpleDateFormat(DATE_IN_FULL, Locale.getDefault())
        binding.selectDateText.text = pattern.format(Date(timestamp))
    }

    private fun handleSubmitButton() {
        binding.buttonSubmit.setOnClickListener {
            handleCreateTimelineItem()
        }
    }

    private fun handleCloseDialog() {
        binding.closeDialog.setOnClickListener {
            dismiss()
        }
    }

    private fun isValidData(): Boolean {
        return selectedDate != null && !binding.subjectName.editText().text.isNullOrBlank()
    }

    private fun handleCreateTimelineItem() {
        if(isValidData()) {
            val timelineItem = CreateTimelineItemEntity().apply {
                id = Random().nextInt()
                date = selectedDate!!
                subjectName = binding.subjectName.editText().text.toString()
            }
            timelineItemDAO.createTimelineItems(timelineItem)
            mContract.createTimelineItemListener(timelineItem)
            dismiss()
        }else{
            Toast.makeText(requireContext(),"Preencha todos os campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setDefaultStyleSelectDateButton() {
        binding.selectDateText.text = context?.getText(R.string.default_text_select_date)
        binding.selectDateText.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary))
    }

    @SuppressLint("UseCompatLoadingForDrawables", "ResourceAsColor")
    private fun setStyledSelectedDateButton() {
        binding.selectDateButton.setBackgroundDrawable(context?.getDrawable(R.drawable.bg_rounded_blue_light_with_border_primary))
        binding.selectDateText.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary))
    }

    private fun closeSelectDateCalendar() {
        binding.calendar.visibility = View.GONE
    }

    private fun showSelectDateCalendar() {
        binding.calendar.visibility = View.VISIBLE
    }

    companion object {
        const val TAG = "CreateTimelineItemDialog"
        const val DATE_IN_FULL = "dd 'de' MMMM"
    }
}