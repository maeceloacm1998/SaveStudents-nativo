package com.savestudents.features.curriculum.ui

import android.os.Bundle
import android.view.View
import com.savestudents.components.R
import androidx.lifecycle.lifecycleScope
import com.savestudents.core.utils.BaseFragment
import com.savestudents.features.NavigationActivity
import com.savestudents.features.databinding.FragmentCurriculumBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class CurriculumFragment() :
    BaseFragment<FragmentCurriculumBinding, NavigationActivity>(FragmentCurriculumBinding::inflate),
    CurriculumContract.View {

    override val presenter: CurriculumContract.Presenter by inject { parametersOf(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity?.title = "Grade Currícular"
        lifecycleScope.launch {
            presenter.fetchMatters()
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
}