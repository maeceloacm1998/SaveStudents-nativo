package com.example.savestudents.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.savestudents.constants.FirestoreDbConstants
import com.example.savestudents.controller.FilterOptionsController
import com.example.savestudents.databinding.ActivityFilterOptionsBinding
import com.example.savestudents.model.contract.FilterOptionsContract
import com.example.savestudents.viewModel.FilterOptionsViewModel

class FilterOptionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilterOptionsBinding
    private val filterController by lazy { FilterOptionsController(contract) }
    private lateinit var mViewModel: FilterOptionsViewModel

    private var checkboxRadioSelected: String = ""
    private var checkboxSelectedList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(
                application
            )
        ).get(FilterOptionsViewModel()::class.java)

        handleFilterOptionsController()
        fetchFilterOptions()
        observer()
        listeners()
    }

    private fun listeners() {
        clearFilters()
        goBackClickButton()
        applyFilters()
    }

    private fun clearFilters() {
        binding.headerOptions.clearButton.setOnClickListener {
            checkboxSelectedList.clear()
            checkboxRadioSelected = ""
            filterController.clearFilters()
        }
    }

    private fun goBackClickButton() {
        binding.headerOptions.goBackButton.setOnClickListener {

        }
    }

    private fun applyFilters() {
        binding.applyFiltersButton.setOnClickListener {

        }
    }

    private fun fetchFilterOptions() {
        mViewModel.apply {
            getShiftOptions(FirestoreDbConstants.Collections.FILTER_OPTIONS_SHIFT, "order")
            getPeriodOptions(FirestoreDbConstants.Collections.FILTER_OPTIONS_PERIOD, "name")
        }
    }

    private fun handleFilterOptionsController() {
        binding.filterOptionsList.apply {
            setController(filterController)
            layoutManager = LinearLayoutManager(context)
            requestModelBuild()
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

    private fun setRadioSelected(title: String) {
        checkboxRadioSelected = title
        filterController.setRadioSelected(title)
    }

    private fun setCheckboxSelected(title: String) {
        checkboxSelectedList.add(title)
        filterController.setCheckboxSelected(checkboxSelectedList)
    }

    private fun removeCheckboxSelected(title: String) {
        checkboxSelectedList.remove(title)
        filterController.setCheckboxSelected(checkboxSelectedList)
    }

    private val contract = object : FilterOptionsContract {
        override fun clickCheckCheckboxListener(title: String) {
            setCheckboxSelected(title)
        }

        override fun clickUncheckCheckboxListener(title: String) {
            removeCheckboxSelected(title)
        }

        override fun clickCheckCheckboxRadioListener(title: String) {
            setRadioSelected(title)
        }
    }
}