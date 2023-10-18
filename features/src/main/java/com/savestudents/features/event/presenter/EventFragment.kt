package com.savestudents.features.event.presenter

import android.os.Bundle
import android.view.View
import com.savestudents.core.utils.BaseFragment
import com.savestudents.features.NavigationActivity
import com.savestudents.features.databinding.FragmentEventBinding

class EventFragment : BaseFragment<FragmentEventBinding, NavigationActivity>(FragmentEventBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}