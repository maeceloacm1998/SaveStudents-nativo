package com.example.savestudents.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.savestudents.constants.FirestoreDbConstants
import com.example.savestudents.controller.FilterOptionsController
import com.example.savestudents.databinding.ActivityFilterOptionsBinding
import com.example.savestudents.viewModel.FilterOptionsViewModel

class FilterOptionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilterOptionsBinding
    private val filterController = FilterOptionsController()
    private lateinit var mViewModel: FilterOptionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(
                application
            )
        ).get(FilterOptionsViewModel()::class.java)

        fetchFilterOptions()
        handleFilterOptionsController()
        observer()
    }

    private fun fetchFilterOptions() {
        mViewModel.getPeriodOptions(FirestoreDbConstants.Collections.FILTER_OPTIONS_SHIFT)
        mViewModel.getShiftOptions(FirestoreDbConstants.Collections.FILTER_OPTIONS_PERIOD)
    }

    private fun handleFilterOptionsController() {
        binding.filterOptionsList.apply {
            setController(filterController)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observer() {
        mViewModel.shiftOptions.observe(this) { observe ->
            filterController.setShiftOptions(observe)
        }

        mViewModel.periodOptions.observe(this) { observe ->
            filterController.setPeriodOptions(observe)
        }
    }
}