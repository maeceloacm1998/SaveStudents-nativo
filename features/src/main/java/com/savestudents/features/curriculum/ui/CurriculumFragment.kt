package com.savestudents.features.curriculum.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.savestudents.components.R
import com.savestudents.features.R.string
import com.savestudents.core.utils.BaseFragment
import com.savestudents.core.utils.DateUtils
import com.savestudents.features.NavigationActivity
import com.savestudents.features.addMatter.models.Event
import com.savestudents.features.databinding.FragmentCurriculumBinding
import com.savestudents.features.home.ui.EventItemAdapter
import com.shrikanthravi.collapsiblecalendarview.data.Day
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class CurriculumFragment :
    BaseFragment<FragmentCurriculumBinding, NavigationActivity>(FragmentCurriculumBinding::inflate),
    CurriculumContract.View {

    override val presenter: CurriculumContract.Presenter by inject { parametersOf(this) }
    private val adapterCurriculum: EventItemAdapter = EventItemAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity?.handleTitleToolbar("Grade Currícular")
        init()
        setupViews()
    }

    private fun init() {
        val (day, month, year) = DateUtils.getCurrentDate()
        lifecycleScope.launch {
            presenter.fetchMatters(binding.calendar.month)
            presenter.fetchEventsWithDate(day, month, year)
        }
    }

    private fun setupViews() {
        binding.run {
            eventsRv.run {
                adapter = adapterCurriculum
                layoutManager = LinearLayoutManager(context)
            }

            calendar.setCalendarListener(object : CollapsibleCalendar.CalendarListener {
                override fun onDaySelect() {
                    val daySelected: Day = calendar.selectedDay

                    lifecycleScope.launch {
                        presenter.fetchEventsWithDate(
                            day = daySelected.day,
                            month = daySelected.month,
                            year = daySelected.year
                        )
                    }
                }

                override fun onItemClick(v: View?) {}
                override fun onDataUpdate() {}
                override fun onMonthChange() {
                    lifecycleScope.launch {
                        presenter.fetchMatters(calendar.month)
                    }
                }
                override fun onWeekChange(position: Int) {}
            })
        }
    }

    override fun error(visibility: Boolean) {
        binding.run {
            error.root.isVisible = visibility
            container.isVisible = false

            error.message.text = getString(string.curriculum_error_message)
            error.button.setOnClickListener { init() }

        }
    }

    override fun loadingScreen(visibility: Boolean) {
        binding.run {
            if (visibility) {
                loading.root.isVisible = true
                container.isVisible = false
            } else {
                loading.root.isVisible = false
                container.isVisible = true
            }
        }
    }

    override fun showNotEvents(visibility: Boolean) {
        binding.calendarError.root.isVisible = visibility
    }

    override fun setEvent(year: Int, month: Int, day: Int) {
        binding.calendar.addEventTag(
            year,
            month,
            day,
            requireContext().getColor(R.color.primary)
        )
    }

    override fun updateEventList(eventList: List<Event.EventItem>, day: Int, month: Int) {
        adapterCurriculum.updateData(eventList, day, month)
    }
}