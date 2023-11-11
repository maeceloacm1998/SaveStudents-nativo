package com.savestudents.components.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.savestudents.components.R
import com.savestudents.components.databinding.CalendarCustomViewBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.util.Locale

class CalendarCustom(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    private var binding: CalendarCustomViewBinding = CalendarCustomViewBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    private var onClickDayListener: (day: LocalDate) -> Unit = {}

    var selectedDate: LocalDate? = null
        set(value) {
            field = value
            updateCalendar()
        }

    var eventCalendar: List<EventCalendar> = mutableListOf()
        set(value) {
            field = value
            updateCalendar()
        }

    init {
        setLayout(attrs)
        setupViews()
    }

    fun onClickDayListener(listener: (day: LocalDate) -> Unit) {
        onClickDayListener = listener
    }

    private fun updateCalendar() {
        binding.exFiveCalendar.adapter?.notifyDataSetChanged()
    }

    private fun setLayout(attr: AttributeSet?) {
        attr?.let { attributeSet: AttributeSet ->
            val attributes =
                context.obtainStyledAttributes(attributeSet, R.styleable.CalendarCustom)
            attributes.recycle()
        }
    }

    private fun setupViews() {
        setupCalendar()
        handleCalendarMonthInteractions()
    }

    private fun setupCalendar() {
        val daysOfWeek = daysOfWeek()
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(MONTH_TO_SUBTRACT)
        val endMonth = currentMonth.plusMonths(MONTH_TO_ADD)

        binding.exFiveMonthYearText.text = getMonthNameByMonthYear(currentMonth.toString())
        binding.exFiveCalendar.setup(startMonth, endMonth, daysOfWeek.first())
        binding.exFiveCalendar.scrollToMonth(currentMonth)
        configureBinders()
    }

    private fun handleCalendarMonthInteractions() {
        binding.exFiveCalendar.monthScrollListener = { month ->
            binding.exFiveMonthYearText.text = getMonthNameByMonthYear(month.yearMonth.toString())

            selectedDate?.let {
                selectedDate = null
                binding.exFiveCalendar.notifyDateChanged(it)
            }
        }

        binding.exFiveNextMonthImage.setOnClickListener {
            binding.exFiveCalendar.findFirstVisibleMonth()?.let {
                binding.exFiveCalendar.smoothScrollToMonth(it.yearMonth.nextMonth)
            }
        }

        binding.exFivePreviousMonthImage.setOnClickListener {
            binding.exFiveCalendar.findFirstVisibleMonth()?.let {
                binding.exFiveCalendar.smoothScrollToMonth(it.yearMonth.previousMonth)
            }
        }
    }

    private fun getMonthNameByMonthYear(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM", Locale("pt", "BR"))
        val outputFormat = SimpleDateFormat("MMMM - yyyy", Locale("pt", "BR"))

        kotlin.runCatching {
            val formattedDate = outputFormat.format(inputFormat.parse(date))
            val parts = formattedDate.split(" - ")
            if (parts.size == 2) {
                val capitalizedMonth = parts[0].replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
                return "$capitalizedMonth - ${parts[1]}"
            }
        }

        return ""
    }


    private fun configureBinders() {
        handleDayBinder()
        handleMonthBinder()
    }

    private fun handleDayBinder() {
        binding.exFiveCalendar.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(
                view = view,
                calendarBinding = binding,
                selectedDate = selectedDate
            )

            override fun bind(container: DayViewContainer, data: CalendarDay) {
                val dayOfMonth = data.date.dayOfMonth.toString()

                defaultSetupBinders(container = container, dayOfMonth = dayOfMonth, data = data)
                handleCalendarDayClick(container)
                handleSelectDate(container = container, data = data)
            }
        }
    }

    private fun handleMonthBinder() {
        binding.exFiveCalendar.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)
                override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                    val legendContainer = container.bindingLegend
                    if (legendContainer.tag == null) {
                        handleDaysOfWeekHeader(legendContainer = legendContainer, data = data)
                    }
                }
            }
    }

    private fun handleDaysOfWeekHeader(
        legendContainer: LinearLayout,
        data: CalendarMonth
    ) {
        legendContainer.tag = data.yearMonth

        val daysOfWeek = arrayOf("dom.", "seg.", "ter.", "qua.", "qui.", "sex.", "sáb.")
        legendContainer.children.map { it as TextView }
            .forEachIndexed { index, tv ->
                tv.text = daysOfWeek[index]
            }
    }

    private fun defaultSetupBinders(
        container: DayViewContainer,
        dayOfMonth: String,
        data: CalendarDay
    ) {
        container.binding.run {
            exFiveDayText.text = dayOfMonth
            container.day = data
            exFiveDayFlightTop.background = null
            exFiveDayFlightBottom.background = null
        }
    }

    private fun handleCalendarDayClick(container: DayViewContainer) {
        container.onClickCalendarListener { date ->
            selectedDate = date
            onClickDayListener(date)
            binding.exFiveCalendar.adapter?.notifyDataSetChanged()
        }
    }

    private fun handleSelectDate(
        container: DayViewContainer,
        data: CalendarDay
    ) {
        val context = container.binding.root.context
        val numberDay = container.binding.exFiveDayText
        val layout = container.binding.exFiveDayLayout
        val flightTopView = container.binding.exFiveDayFlightTop
        val flightBottomView = container.binding.exFiveDayFlightBottom

        if (isMonthPosition(data)) {
            numberDay.setTextColor(context.getColor(R.color.white))

            if (isDateSelected(data)) {
                layout.setBackgroundResource(R.drawable.bg_rounded_select_day)
            } else {
                layout.setBackgroundResource(if (isCurrentDay(data)) R.drawable.bg_rounded_default else EMPTY_DRAWABLE)
            }

            val filterEvent: List<EventCalendar> = eventCalendar.filter { it.date == data.date }
            if (existEvent(filterEvent)) {
                val event = filterEvent.first()
                event.eventCalendarType.map { type ->
                    if (type == EventCalendarType.EVENT) {
                        flightTopView.setBackgroundResource(type.drawableInt)
                    }

                    if (type == EventCalendarType.MATTER) {
                        flightBottomView.setBackgroundResource(type.drawableInt)
                    }
                }
            }
        } else {
            numberDay.setTextColor(context.getColor(R.color.gray_scale_800))
            layout.background = null
        }
    }

    private fun isMonthPosition(data: CalendarDay) = data.position == DayPosition.MonthDate
    private fun isDateSelected(data: CalendarDay) = selectedDate == data.date
    private fun isCurrentDay(data: CalendarDay) = data.date == LocalDate.now()
    private fun existEvent(filterEvent: List<EventCalendar>) = filterEvent.isNotEmpty()

    companion object {
        private const val MONTH_TO_SUBTRACT = 200L
        private const val MONTH_TO_ADD = 200L
        private const val EMPTY_DRAWABLE = 0
    }
}