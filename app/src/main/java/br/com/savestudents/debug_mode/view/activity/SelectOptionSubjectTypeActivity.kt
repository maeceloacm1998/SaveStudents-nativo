package br.com.savestudents.debug_mode.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.savestudents.constants.FirestoreDbConstants
import br.com.savestudents.controller.FilterOptionsController
import br.com.savestudents.databinding.ActivitySelectOptionSubjectTypeBinding
import br.com.savestudents.model.contract.FilterOptionsContract
import br.com.savestudents.viewModel.FilterOptionsViewModel

class SelectOptionSubjectTypeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectOptionSubjectTypeBinding
    private lateinit var mViewModel: FilterOptionsViewModel
    private val controller by lazy { FilterOptionsController(mContract) }

    private var checkboxRadioSelected: String = ""
    private var checkboxSelectedList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectOptionSubjectTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(
                application
            )
        ).get(FilterOptionsViewModel()::class.java)

        handleCacheValuesCheckbox()
        initController()
        fetchFilterOptions()
        observers()
        listeners()
    }

    private fun listeners() {
        clearFilters()
        goBackClickButton()
        saveFilters()
    }

    private fun clearFilters() {
        binding.headerOptions.clearButton.setOnClickListener {
            checkboxSelectedList.clear()
            checkboxRadioSelected = ""
            controller.clearFilters()
        }
    }

    private fun goBackClickButton() {
        binding.headerOptions.goBackButton.setOnClickListener {
            finish()
        }
    }

    private fun saveFilters() {
        binding.applyFiltersButton.setOnClickListener {
            AllSubjectsListActivity.saveFiltersSelected(checkboxRadioSelected, checkboxSelectedList)
            AllSubjectsListActivity.isFiltered = true
            finish()
        }
    }

    private fun initController() {
        binding.filterOptionsList.apply {
            setController(controller)
            layoutManager = LinearLayoutManager(context)
            requestModelBuild()
        }
    }

    private fun observers() {
        mViewModel.periodOptions.observe(this) { result ->
            controller.setPeriodOptions(result)
        }

        mViewModel.shiftOptions.observe(this) { result ->
            controller.setShiftOptions(result)
        }
    }

    private fun setRadioSelected(title: String) {
        checkboxRadioSelected = title
        controller.setRadioSelected(title)
    }

    private fun setCheckboxSelected(title: String) {
        checkboxSelectedList.add(title)
        controller.setCheckboxSelected(checkboxSelectedList)
    }

    private fun removeCheckboxSelected(title: String) {
        checkboxSelectedList.remove(title)
        controller.setCheckboxSelected(checkboxSelectedList)
    }

    private fun handleCacheValuesCheckbox() {
        val intent = intent.extras
        val checkboxSelectedList = intent?.getStringArray(CHECKBOX_SELECTED_LIST)
        val checkboxRadioSelected = intent?.getString(CHECKBOX_RADIO_SELECTED)

        if (!checkboxRadioSelected.isNullOrBlank()) {
            this.checkboxRadioSelected = checkboxRadioSelected
            controller.setRadioSelected(checkboxRadioSelected)
        }

        if (!checkboxSelectedList.isNullOrEmpty()) {
            this.checkboxSelectedList = checkboxSelectedList.toMutableList()
            controller.setCheckboxSelected(checkboxSelectedList.toMutableList())
        }
    }

    private fun fetchFilterOptions() {
        mViewModel.apply {
            getShiftOptions(FirestoreDbConstants.Collections.FILTER_OPTIONS_SHIFT, "order")
            getPeriodOptions(FirestoreDbConstants.Collections.FILTER_OPTIONS_PERIOD, "name")
        }
    }

    private val mContract = object : FilterOptionsContract {
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

        fun newInstance(
            checkboxRadioSelected: String,
            checkboxSelectedList: Array<String>,
            context: Context
        ): Intent {
            val intent = Intent(context, SelectOptionSubjectTypeActivity::class.java)
            saveBundle(checkboxRadioSelected, checkboxSelectedList, intent)
            return intent
        }

        private fun saveBundle(
            checkboxRadioSelected: String,
            checkboxSelectedList: Array<String>,
            intent: Intent
        ) {
            val bundle = Bundle().apply {
                this.putString(CHECKBOX_RADIO_SELECTED, checkboxRadioSelected)
                this.putStringArray(CHECKBOX_SELECTED_LIST, checkboxSelectedList)
            }
            intent.putExtras(bundle)
        }
    }
}