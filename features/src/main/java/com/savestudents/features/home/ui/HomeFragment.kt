package com.savestudents.features.home.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.savestudents.core.utils.BaseFragment
import com.savestudents.features.databinding.FragmentHomeBinding
import com.savestudents.features.NavigationActivity

class HomeFragment :
    BaseFragment<FragmentHomeBinding, NavigationActivity>(FragmentHomeBinding::inflate) {

    private val adapterHome by lazy { HomeAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity?.handleDrawerMenu()
        binding.homeRv.run {
            adapter = adapterHome
            layoutManager = LinearLayoutManager(context)
        }

        // TODO fazere request no back e inserir na lista
//        adapterHome.submitList(x)
    }
}