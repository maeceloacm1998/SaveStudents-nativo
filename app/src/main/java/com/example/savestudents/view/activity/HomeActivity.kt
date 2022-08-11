package com.example.savestudents.view.activity

import android.content.Context
import android.content.Intent
import android.content.res.Resources
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
import com.example.savestudents.controller.HeaderHomeActivityController
import com.example.savestudents.controller.HomeActivityController
import com.example.savestudents.databinding.ActivityHomeBinding
import com.example.savestudents.model.contract.HeaderHomeActivityContract
import com.example.savestudents.viewModel.HomeViewModel


class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private val headerHomeActivityController by lazy { HeaderHomeActivityController(headerContract) }
    private val homeActivityController = HomeActivityController()
    private lateinit var mViewModel: HomeViewModel

    private var checkboxRadioSelected: String = ""
    private var checkboxSelectedList: MutableList<String> = mutableListOf()

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
        handleCacheFiltersSelected()
    }

    private fun handleCacheFiltersSelected() {
        val intent = intent.extras
        val checkboxSelectedList = intent?.getStringArray(CHECKBOX_SELECTED_LIST)
        val checkboxRadioSelected = intent?.getString(CHECKBOX_RADIO_SELECTED)

        if (!checkboxRadioSelected.isNullOrBlank() && !checkboxSelectedList.isNullOrEmpty()) {
            this.checkboxRadioSelected = checkboxRadioSelected
            this.checkboxSelectedList = checkboxSelectedList.toMutableList()
        }
    }

    private fun controllers() {
        handleHeaderController()
        handleHomeController()
    }

    private fun observers() {
        mViewModel.subjectList.observe(this) { observe ->
            homeActivityController.setSubjectList(observe)
        }
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

    private val headerContract = object : HeaderHomeActivityContract {
        override fun clickFilterButton() {
            startActivity(
                FilterOptionsActivity.newInstance(
                    applicationContext,
                    checkboxRadioSelected,
                    checkboxSelectedList.toTypedArray()
                )
            )
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

    companion object {
        private const val CHECKBOX_RADIO_SELECTED = "checkboxRadioSelected"
        private const val CHECKBOX_SELECTED_LIST = "checkboxSelectedList"

        @JvmStatic
        fun newInstance(
            context: Context,
            checkboxRadioSelected: String,
            checkboxSelectedList: Array<String>
        ): Intent {
            val intent = Intent(context, HomeActivity::class.java)
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
                this.putStringArray(
                    CHECKBOX_SELECTED_LIST,
                    checkboxSelectedList
                )
            }
            intent.putExtras(bundle)
        }
    }
}

