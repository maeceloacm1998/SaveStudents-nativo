package br.oficial.savestudents.debug_mode.view.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.oficial.savestudents.R
import br.oficial.savestudents.databinding.DialogCreateTimelineItemBinding
import br.oficial.savestudents.debug_mode.controller.SelectTimelineTypeController
import br.oficial.savestudents.debug_mode.model.contract.EditTimelineItemDialogContract
import br.oficial.savestudents.debug_mode.model.contract.SelectTimelineTypeContract
import br.oficial.savestudents.debug_mode.viewModel.CreateTimelineViewModel
import com.example.data_transfer.model.CreateTimelineItem
import com.example.data_transfer.model.entity.CreateTimelineItemEntity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class EditTimelineItemDialog(
    private val mContract: EditTimelineItemDialogContract,
    val timelineItem: CreateTimelineItem
) : DialogFragment() {
    private lateinit var binding: DialogCreateTimelineItemBinding
    private val controller by lazy { SelectTimelineTypeController(selectTimelineTypeContract) }
    private val viewModel by lazy { CreateTimelineViewModel(requireContext()) }
    private var selectedDate: Long? = null
    private var modifiedItem: Boolean = false
    private var timelineType: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCreateTimelineItemBinding.inflate(inflater, container, false)
        observers()
        handleSelectTimelineTypeController()

        fetchTimelineTypes()
        setDefaultStyleSelectDateButton()
        handleModifiedTimelineItem()
        handleTimelineItemName()
        handleSelectDateButton()
        handleSelectDateInCalendar()
        setDateSelected()
        handleSubmitButton()
        handleCloseDialog()
        closeSelectDateCalendar()
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.Theme_Dialog)
    }

    private fun observers() {
        viewModel.timelineTypes.observe(this) { observer ->
            controller.apply {
                setTimelineTypesList(observer)
                if(timelineItem.type.isEmpty()) {
                    setTimelineTypesSelected("Matéria")
                } else {
                    setTimelineTypesSelected(timelineItem.type)
                }
            }
        }
    }

    private fun handleSelectTimelineTypeController() {
        binding.typesRv.apply {
            setController(controller)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            requestModelBuild()
        }
    }

    private fun fetchTimelineTypes() {
        viewModel.getTimelineTypes()
    }

    private fun handleSelectDateButton() {
        binding.selectDateButton.setOnClickListener {
            showSelectDateCalendar()
        }
    }

    private fun handleModifiedTimelineItem() {
        binding.subjectName.editText().addTextChangedListener {
            modifiedItem = it.toString() != timelineItem.subjectName
        }
    }

    private fun handleTimelineItemName() {
        binding.subjectName.editText().setText(timelineItem.subjectName)
    }

    private fun setDateSelected() {
        binding.calendar.setDate(timelineItem.date, true, true)
        handleSelectDateTextButton(timelineItem.date)
        selectedDate = timelineItem.date
    }

    private fun handleSelectDateInCalendar() {
        binding.calendar.setOnDateChangeListener { calendarView, year, month, day ->
            selectedDate = getTimestamp(day, month, year)
            modifiedItem = true
            handleSelectDateTextButton(getTimestamp(day, month, year))
        }
    }

    fun getTimestamp(day: Int, month: Int, year: Int): Long {
        val date = LocalDate.of(year, month + 1, day)
        return date.atStartOfDay(ZoneOffset.UTC).toEpochSecond()
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

    private fun handleCreateTimelineItem() {
        if (modifiedItem) {
            val timelineItem = CreateTimelineItemEntity().apply {
                id = timelineItem.id
                date = selectedDate!!
                subjectName = binding.subjectName.editText().text.toString()
                type = timelineType
            }
            mContract.editTimelineItemListener(timelineItem)
            dismiss()
        } else {
            Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setDefaultStyleSelectDateButton() {
        binding.selectDateText.text = context?.getText(R.string.default_text_select_date)
        binding.selectDateText.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.primary
            )
        )
    }

    @SuppressLint("UseCompatLoadingForDrawables", "ResourceAsColor")
    private fun setStyledSelectedDateButton() {
        binding.selectDateButton.setBackgroundDrawable(context?.getDrawable(R.drawable.bg_rounded_blue_light_with_border_primary))
        binding.selectDateText.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.primary
            )
        )
    }

    private fun closeSelectDateCalendar() {
        binding.calendar.visibility = View.GONE
    }

    private fun showSelectDateCalendar() {
        binding.calendar.visibility = View.VISIBLE
    }

    private fun handleTimelineTypeSelected(typeSelected: String) {
        controller.setTimelineTypesSelected(typeSelected)
        timelineType = typeSelected
        modifiedItem = true
    }

    private val selectTimelineTypeContract = object : SelectTimelineTypeContract {
        override fun clickTimelineTypeListener(typeSelected: String) {
            handleTimelineTypeSelected(typeSelected)
        }
    }

    companion object {
        const val TAG = "EditTimelineItemDialog"
        const val DATE_IN_FULL = "dd 'de' MMMM"
    }
}