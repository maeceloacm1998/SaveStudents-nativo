package com.savestudents.features.home.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.savestudents.core.utils.BaseFragment
import com.savestudents.features.databinding.FragmentHomeBinding
import com.savestudents.features.NavigationActivity
import com.savestudents.features.addMatter.models.Event
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HomeFragment :
    BaseFragment<FragmentHomeBinding, NavigationActivity>(FragmentHomeBinding::inflate),
    HomeContract.View {
    override val presenter: HomeContract.Presenter by inject { parametersOf(this) }

    private val adapterHome by lazy { HomeAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity?.handleDrawerMenu()

        setupViews()
        lifecycleScope.launch {
            presenter.getEvents()
        }
    }

    private fun setupViews() {
        binding.homeRv.run {
            adapter = adapterHome
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun loading(loading: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setEventList(eventList: List<Event>) {
        adapterHome.submitList(eventList)
    }
}
