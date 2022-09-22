package br.com.savestudents.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.savestudents.constants.CreateSubjectConstants
import br.com.savestudents.constants.FirestoreDbConstants
import br.com.savestudents.controller.SelectOptionsController
import br.com.savestudents.databinding.ActivitySelectOptionBinding
import br.com.savestudents.model.contract.SelectOptionsContract
import br.com.savestudents.viewModel.FilterOptionsViewModel

class SelectOptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectOptionBinding
    private lateinit var mViewModel: FilterOptionsViewModel
    private val controller by lazy { SelectOptionsController(contract) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectOptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(FilterOptionsViewModel::class.java)

        controller()
        fetchListData()
        observers()
        handleBackButton()
    }

    private fun controller() {
        binding.filterOptionsList.apply {
            setController(controller)
            layoutManager = LinearLayoutManager(applicationContext)
            requestModelBuild()
        }

        setLoading(true)
    }

    private fun setTitle(title: String) {
        binding.headerOptions.textTitle.text = title
    }

    private fun fetchListData() {
        val filterOption = intent?.getStringExtra(FILTER_OPTION)

        when (filterOption) {
            CreateSubjectConstants.Filter.PERIOD_FIELD -> {
                setTitle("Selecione um Periodo")
                mViewModel.getPeriodOptions(
                    FirestoreDbConstants.Collections.FILTER_OPTIONS_PERIOD,
                    "name"
                )
            }

            CreateSubjectConstants.Filter.SHIFT_FIELD -> {
                setTitle("Selecione um Turno")
                mViewModel.getShiftOptions(
                    FirestoreDbConstants.Collections.FILTER_OPTIONS_SHIFT,
                    "order"
                )
            }
        }
    }

    private fun setLoading(status: Boolean) {
        controller.setLoading(status)
    }

    private fun observers() {
        mViewModel.periodOptions.observe(this) { observe ->
            controller.setOptionsList(observe)
            setLoading(false)
        }

        mViewModel.shiftOptions.observe(this) { observe ->
            controller.setOptionsList(observe)
            setLoading(false)
        }
    }

    private fun handleBackButton() {
        binding.headerOptions.goBackButton.setOnClickListener {
            finish()
        }
    }

    private val contract = object : SelectOptionsContract {
        override fun clickedCheckboxListener(optionSelected: String) {
            val filterOption = intent?.getStringExtra(FILTER_OPTION)
            filterOption?.let { CreateSubjectActivity.setOptionSelected(it, optionSelected) }
            finish()
        }
    }

    companion object {
        private const val FILTER_OPTION = "filter_option"

        fun newInstance(context: Context, filterOption: String): Intent {
            val intent = Intent(context, SelectOptionActivity::class.java)
            saveBundle(intent, filterOption)
            return intent
        }

        private fun saveBundle(intent: Intent, filterOption: String) {
            val bundle = Bundle().apply {
                putString(FILTER_OPTION, filterOption)
            }
            intent.putExtras(bundle)
        }
    }
}