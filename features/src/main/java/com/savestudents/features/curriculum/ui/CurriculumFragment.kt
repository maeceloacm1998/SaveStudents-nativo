package com.savestudents.features.curriculum.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.savestudents.components.R
import com.savestudents.core.utils.BaseFragment
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
        parentActivity?.title = "Grade Currícular"
        init()
        setupViews()
    }

    private fun init() {
        lifecycleScope.launch {
            presenter.fetchMatters()
        }
    }

    private fun setupViews() {
        binding.run {
            eventsRv.run {
                adapter = adapterCurriculum
                layoutManager = LinearLayoutManager(context)
            }

            calendar.setCalendarListener(object : CollapsibleCalendar.CalendarListener {
                @RequiresApi(Build.VERSION_CODES.O)
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
                override fun onMonthChange() {}
                override fun onWeekChange(position: Int) {}
            })
        }
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