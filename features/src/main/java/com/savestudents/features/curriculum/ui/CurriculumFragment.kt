package com.savestudents.features.curriculum.ui

import android.os.Bundle
import android.view.View
import com.savestudents.core.utils.BaseFragment
import com.savestudents.features.NavigationActivity
import com.savestudents.features.databinding.FragmentCurriculumBinding

class CurriculumFragment :
    BaseFragment<FragmentCurriculumBinding, NavigationActivity>(FragmentCurriculumBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity?.title = "Grade Currícular"
        binding.calendar.expand(1)
    }
}