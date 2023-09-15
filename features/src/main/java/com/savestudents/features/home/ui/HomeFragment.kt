package com.savestudents.features.home.ui

import android.os.Bundle
import android.view.View
import com.savestudents.core.utils.BaseFragment
import com.savestudents.features.databinding.FragmentHomeBinding
import com.savestudents.features.NavigationActivity

class HomeFragment : BaseFragment<FragmentHomeBinding, NavigationActivity>(FragmentHomeBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity?.handleDrawerMenu()
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {

            }
    }
}