package com.savestudents.features.curriculum.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.leinardi.android.speeddial.SpeedDialActionItem
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
        parentActivity?.goBackPressed {
            findNavController().navigateUp()
        }

        init()
        setupViews()
        handleFabButton()
    }

    private fun init() {
        val (day, month, year) = DateUtils.getCurrentDate()
        lifecycleScope.launch {
            presenter.start()
            presenter.fetchMatters(binding.calendar.month)
            presenter.fetchEventsWithDate(day, month, year)
        }
    }

    private fun handleFabButton() {
        binding.fabButton.run {
            addActionItem(
                SpeedDialActionItem.Builder(
                    com.savestudents.features.R.id.create_events,
                    R.drawable.ic_event
                )
                    .setFabBackgroundColor(requireContext().getColor(R.color.primary))
                    .setFabImageTintColor(requireContext().getColor(R.color.white))
                    .setLabel(getString(string.curriculum_calendar_fab_event))
                    .create()
            )
            addActionItem(
                SpeedDialActionItem.Builder(
                    com.savestudents.features.R.id.create_matter,
                    R.drawable.ic_matter
                )
                    .setFabBackgroundColor(requireContext().getColor(R.color.primary))
                    .setFabImageTintColor(requireContext().getColor(R.color.white))
                    .setLabel(getString(string.curriculum_calendar_fab_matter))
                    .create()
            )

            setOnActionSelectedListener { actionItem ->
                when (actionItem.id) {
                    com.savestudents.features.R.id.create_matter -> {
                        findNavController().navigate(com.savestudents.features.R.id.addMatterFragment)
                    }

                    com.savestudents.features.R.id.create_events -> {
                        findNavController().navigate(com.savestudents.features.R.id.addMatterFragment)
                    }
                }
                false
            }
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
                            month = daySelected.month + 1,
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

    override fun error() {
        binding.run {
            error.root.isVisible = true
            loading.root.isVisible = false
            container.isVisible = false

            error.message.text = getString(string.curriculum_error_message)
            error.button.setOnClickListener { init() }

        }
    }

    override fun loadingScreen(visibility: Boolean) {
        binding.run {
            if (visibility) {
                loading.root.isVisible = true
                error.root.isVisible = false
                container.isVisible = false
            } else {
                loading.root.isVisible = false
                error.root.isVisible = false
                container.isVisible = true
            }
        }
    }

    override fun showNotEvents(visibility: Boolean) {
        binding.calendarError.container.isVisible = visibility
        binding.eventsRv.isVisible = !visibility
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