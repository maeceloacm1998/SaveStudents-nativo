package com.example.savestudents.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
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

        handleCacheValuesCheckbox()
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
            finish()
        }
    }

    private fun applyFilters() {
        binding.applyFiltersButton.setOnClickListener {
          val intent =  HomeActivity.newInstance(applicationContext, checkboxRadioSelected, checkboxSelectedList as ArrayList<String>)
            startActivity(intent)
            finish()
        }
    }

    private fun fetchFilterOptions() {
        mViewModel.apply {
            getShiftOptions(FirestoreDbConstants.Collections.FILTER_OPTIONS_SHIFT, "order")
            getPeriodOptions(FirestoreDbConstants.Collections.FILTER_OPTIONS_PERIOD, "name")
        }
    }

    private fun handleCacheValuesCheckbox() {
        val intent = intent.extras
        val checkboxSelectedList = intent?.getStringArray(CHECKBOX_SELECTED_LIST)
        val checkboxRadioSelected = intent?.getString(CHECKBOX_RADIO_SELECTED)

        if (!checkboxRadioSelected.isNullOrBlank() && !checkboxSelectedList.isNullOrEmpty()) {
            this.checkboxRadioSelected = checkboxRadioSelected
            this.checkboxSelectedList = checkboxSelectedList.toMutableList()
            filterController.setRadioSelected(checkboxRadioSelected)
            filterController.setCheckboxSelected(checkboxSelectedList.toMutableList())
        }


        intent?.getString(CHECKBOX_RADIO_SELECTED)?.let { filterController.setRadioSelected(it) }
        intent?.getStringArray(CHECKBOX_SELECTED_LIST)
            ?.let { filterController.setCheckboxSelected(it.toMutableList()) }
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
            binding.applyFiltersButton.visibility = View.VISIBLE
        }

        mViewModel.periodOptions.observe(this) { observe ->
            filterController.setPeriodOptions(observe)
            binding.applyFiltersButton.visibility = View.VISIBLE
        }

        mViewModel.handleResponseError.observe(this) { observe ->
            binding.applyFiltersButton.visibility = View.GONE
            filterController.setResponseError(observe)
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

        override fun tryAgainListener() {
            handleCacheValuesCheckbox()
            fetchFilterOptions()
        }
    }

    companion object {
        private const val CHECKBOX_RADIO_SELECTED = "checkboxRadioSelected"
        private const val CHECKBOX_SELECTED_LIST = "checkboxSelectedList"

        @JvmStatic
        fun newInstance(context: Context,checkboxRadioSelected: String, checkboxSelectedList: ArrayList<String>): Intent {
            val intent = Intent(context, FilterOptionsActivity::class.java)
            saveBundle(checkboxRadioSelected,checkboxSelectedList,intent)
            return intent
        }

        private fun saveBundle(checkboxRadioSelected: String, checkboxSelectedList: ArrayList<String>, intent: Intent) {
            val bundle = Bundle().apply {
                this.putString(CHECKBOX_RADIO_SELECTED, checkboxRadioSelected)
                this.putStringArray(CHECKBOX_SELECTED_LIST, checkboxSelectedList.toArray() as Array<out String>?)
            }
            intent.putExtras(bundle)
        }
    }
}