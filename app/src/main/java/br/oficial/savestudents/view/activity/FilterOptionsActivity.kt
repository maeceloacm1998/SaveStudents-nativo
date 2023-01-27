package br.oficial.savestudents.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.br.core.constants.FirestoreDbConstants
import br.oficial.savestudents.controller.FilterOptionsController
import br.oficial.savestudents.databinding.ActivityFilterOptionsBinding
import br.oficial.savestudents.viewModel.FilterOptionsViewModel
import com.example.data_transfer.model.contract.FilterOptionsContract

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
        saveFilters()
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

    private fun saveFilters() {
        binding.applyFiltersButton.setOnClickListener {
            HomeActivity.saveFiltersSelected(checkboxRadioSelected, checkboxSelectedList)
            HomeActivity.isFiltered = true
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

        if (!checkboxRadioSelected.isNullOrBlank()) {
            this.checkboxRadioSelected = checkboxRadioSelected
            filterController.setRadioSelected(checkboxRadioSelected)
        }

        if (!checkboxSelectedList.isNullOrEmpty()) {
            this.checkboxSelectedList = checkboxSelectedList.toMutableList()
            filterController.setCheckboxSelected(checkboxSelectedList.toMutableList())
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
        fun newInstance(
            context: Context,
            checkboxRadioSelected: String,
            checkboxSelectedList: Array<String>
        ): Intent {
            val intent = Intent(context, FilterOptionsActivity::class.java)
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