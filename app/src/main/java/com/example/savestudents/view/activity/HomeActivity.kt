package com.example.savestudents.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.savestudents.R
import com.example.savestudents.constants.FirestoreDbConstants
import com.example.savestudents.controller.HeaderHomeActivityController
import com.example.savestudents.controller.HomeActivityController
import com.example.savestudents.controller.SearchBarController
import com.example.savestudents.databinding.ActivityHomeBinding
import com.example.savestudents.model.contract.HeaderHomeActivityContract
import com.example.savestudents.model.contract.HomeActivityContract
import com.example.savestudents.viewModel.HomeViewModel


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val headerHomeActivityController by lazy { HeaderHomeActivityController(headerContract) }
    private val homeActivityController by lazy { HomeActivityController(homeContract) }
    private val searchBarController = SearchBarController()
    private lateinit var mViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(HomeViewModel()::class.java)

        fetchSubjectList()
        controllers()
        observers()
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        handleCalculateTallestHeader()
        return super.onCreateView(parent, name, context, attrs)
    }

    override fun onResume() {
        super.onResume()
        if (isFiltered) {
            handleFiltersSelected()
        }
    }

    override fun onPause() {
        super.onPause()
        isFiltered = false
    }

    private fun controllers() {
        handleHeaderController()
        handleHomeController()
    }

    private fun observers() {
        mViewModel.subjectList.observe(this) { observe ->
            setError(false)
            homeActivityController.setSubjectList(observe)
        }

        mViewModel.searchList.observe(this) { observe ->
            searchBarController.apply {
                isResponseError(false)
                setLoading(false)
                setSubjectList(observe)
            }
        }

        mViewModel.subjectListError.observe(this) { observe ->
            setError(true)
            homeActivityController.setResponseError(observe)
        }

        mViewModel.searchError.observe(this) { observe ->
            searchBarController.apply {
                isResponseError(true)
                setResponseError(observe)
            }
        }
    }

    private fun setError(status: Boolean) {
        homeActivityController.isResponseError(status)
    }

    private fun fetchSubjectList() {
        mViewModel.getSubjectList()
    }

    private fun handleHeaderController() {
        binding.headerMainView.apply {
            setController(headerHomeActivityController)
            layoutManager = LinearLayoutManager(context)
            requestModelBuild()
        }
    }

    private fun handleHomeController() {
        binding.homeMainView.apply {
            setController(homeActivityController)
            layoutManager = LinearLayoutManager(context)
            requestModelBuild()
        }
    }

    private fun handleCalculateTallestHeader() {
        val layoutTallest = R.layout.activity_home_header_tallest

        (parent?.parent as? ViewGroup)?.let {
            val headerTallest =
                LayoutInflater.from(applicationContext).inflate(layoutTallest, null)
            headerTallest.visibility = View.VISIBLE
            it.addView(headerTallest)
            headerTallest.doOnLayout { view ->
                binding.headerMainView.apply {
                    this.layoutParams.height = view.measuredHeight
                }
                headerTallest.visibility = View.GONE
            }
        }
    }

    private fun handleFiltersSelected() {
        homeActivityController.clearSubjectList()

        if (checkboxRadioSelected.isBlank() && checkboxSelectedList.isEmpty()) {
            fetchSubjectList()
        }
        filterSubjectList()
    }

    private fun filterSubjectList() {
        mViewModel.filterSubjectList(
            FirestoreDbConstants.Collections.SUBJECTS_LIST,
            checkboxSelectedList,
            checkboxRadioSelected
        )
    }

    private val headerContract = object : HeaderHomeActivityContract {
        override fun clickFilterButtonListener() {
            startActivity(
                FilterOptionsActivity.newInstance(
                    applicationContext,
                    checkboxRadioSelected,
                    checkboxSelectedList.toTypedArray()
                )
            )
        }

        override fun clickSearchBarListener() {
            binding.homeMainView.apply {
                setController(searchBarController)
                layoutManager = LinearLayoutManager(context)
                requestModelBuild()
            }
        }

        override fun clickButtonCancelListener() {
            homeActivityController.clearSubjectList()
            binding.homeMainView.apply {
                setController(homeActivityController)
                layoutManager = LinearLayoutManager(context)
                requestModelBuild()
            }
            fetchSubjectList()
        }

        override fun editTextValue(text: String) {
            searchBarController.setLoading(true)
            mViewModel.searchSubjectList(FirestoreDbConstants.Collections.SUBJECTS_LIST, text)
        }
    }

    private val homeContract = object : HomeActivityContract {
        override fun tryAgainListener() {
            fetchSubjectList()
        }
    }

    companion object {
        var checkboxRadioSelected: String = ""
        var checkboxSelectedList: MutableList<String> = mutableListOf()
        var isFiltered = false

        @JvmStatic
        fun newInstance(
            context: Context,
        ): Intent {
            return Intent(context, HomeActivity::class.java)
        }

        fun saveFiltersSelected(
            checkboxRadioSelected: String,
            checkboxSelectedList: MutableList<String>
        ) {
            this.checkboxRadioSelected = checkboxRadioSelected
            this.checkboxSelectedList = checkboxSelectedList
        }
    }
}