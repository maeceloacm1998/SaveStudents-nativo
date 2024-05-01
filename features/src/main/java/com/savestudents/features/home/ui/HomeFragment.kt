package com.savestudents.features.home.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.savestudents.core.utils.BaseFragment
import com.savestudents.features.databinding.FragmentHomeBinding
import com.savestudents.features.NavigationActivity
import com.savestudents.features.addMatter.models.Event
import com.savestudents.features.home.ui.adapter.home.HomeAdapter
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

        lifecycleScope.launch {
            presenter.start()
            presenter.handleEvents()
        }
    }

    override fun onSetupViewsHomeAdapter() {
        binding.homeRv.run {
            adapter = adapterHome
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onSetupViewsErrorScreen() {
        binding.error.button.setOnClickListener {
            lifecycleScope.launch {
                presenter.handleEvents()
                onError(false)
            }
        }
    }

    override fun onLoading(loading: Boolean) {
        binding.loading.shimmerHolder.isVisible = loading
    }

    override fun onError(error: Boolean) {
        binding.error.root.isVisible = error
    }

    override fun onSetEventList(eventList: List<Event>) {
        adapterHome.submitList(eventList)
    }
}
