package com.savestudents.features.curriculum.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.savestudents.components.R
import com.savestudents.components.calendar.EventCalendar
import com.savestudents.components.snackbar.SnackBarCustomType
import com.savestudents.features.R.string
import com.savestudents.core.utils.BaseFragment
import com.savestudents.core.utils.DateUtils.getTimestampCurrentDate
import com.savestudents.features.NavigationActivity
import com.savestudents.features.addMatter.models.Event
import com.savestudents.features.databinding.FragmentCurriculumBinding
import com.savestudents.features.home.ui.adapter.eventItem.EventItemAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.time.LocalDate
import java.time.ZoneId

class CurriculumFragment :
    BaseFragment<FragmentCurriculumBinding, NavigationActivity>(FragmentCurriculumBinding::inflate),
    CurriculumContract.View {

    override val presenter: CurriculumContract.Presenter by inject { parametersOf(this) }
    private val adapterCurriculum: EventItemAdapter = EventItemAdapter(
        showDelete = true,
        clickDeleteEventListener = ::clickDeleteEventListener
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity?.handleTitleToolbar("Grade Curricular")
        parentActivity?.goBackPressed {
            findNavController().popBackStack()
        }

        init()
        setupViews()
        handleFabButton()
    }

    override fun init() {
        val currentDate = getTimestampCurrentDate()
        lifecycleScope.launch {
            presenter.start()
            presenter.fetchMatters()
            presenter.fetchEventsWithDate(currentDate)
        }
    }

    @SuppressLint("NewApi")
    private fun setupViews() {
        binding.run {
            eventsRv.run {
                adapter = adapterCurriculum
                layoutManager = LinearLayoutManager(context)
            }

            binding.calendar.onClickDayListener { localDate ->
                val zoneId = ZoneId.of("America/Sao_Paulo")
                val instant = localDate.atStartOfDay(zoneId).toInstant()
                val timestamp = instant.toEpochMilli()
                lifecycleScope.launch {
                    presenter.fetchEventsWithDate(timestamp)
                }
            }
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
                        findNavController().navigate(com.savestudents.features.R.id.action_curriculumFragment_to_addMatterFragment)
                    }

                    com.savestudents.features.R.id.create_events -> {
                        findNavController().navigate(com.savestudents.features.R.id.action_curriculumFragment_to_eventFragment)
                    }
                }
                false
            }
        }
    }

    private fun clickDeleteEventListener(item: Event.EventItem) {
        lifecycleScope.launch {
            presenter.deleteEvent(item)
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
                fabButton.isVisible = false
                error.root.isVisible = false
                container.isVisible = false
            } else {
                loading.root.isVisible = false
                error.root.isVisible = false
                fabButton.isVisible = true
                container.isVisible = true
            }
        }
    }

    override fun onSetCurrentDate(localDate: LocalDate) {
        binding.calendar.selectedDate = localDate
    }

    override fun showNotEvents(visibility: Boolean) {
        binding.calendarError.container.isVisible = visibility
        binding.eventsRv.isVisible = !visibility
    }

    override fun updateEventList(eventList: List<Event.EventItem>, day: Int, month: Int) {
        adapterCurriculum.updateData(eventList, day, month)
    }

    override fun showSnackBar(message: Int, type: SnackBarCustomType) {
        parentActivity?.showSnackBar(getString(message), type)
    }

    override fun updateCalendar(eventCalendarList: List<EventCalendar>) {
        binding.calendar.eventCalendar = eventCalendarList
    }

    override fun clearCalendarEvents() {
        binding.calendar.eventCalendar = mutableListOf()
    }
}