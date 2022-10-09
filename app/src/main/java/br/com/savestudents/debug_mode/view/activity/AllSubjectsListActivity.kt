package br.com.savestudents.debug_mode.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.savestudents.constants.FirestoreDbConstants
import br.com.savestudents.databinding.ActivityAllSubjectsListBinding
import br.com.savestudents.debug_mode.controller.SearchBarDebugModeController
import br.com.savestudents.debug_mode.controller.SubjectListController
import br.com.savestudents.debug_mode.model.contract.SearchBarContract
import br.com.savestudents.debug_mode.model.contract.SubjectListContract
import br.com.savestudents.debug_mode.viewModel.AllSubjectsListViewModel
import br.com.savestudents.ui_component.confirmationAlertDialog.ConfirmationAlertContract
import br.com.savestudents.ui_component.confirmationAlertDialog.ConfirmationAlertDialog

class AllSubjectsListActivity : AppCompatActivity() {
    lateinit var binding: ActivityAllSubjectsListBinding
    private val viewModel by lazy { AllSubjectsListViewModel(applicationContext) }
    private val searchBarController by lazy { SearchBarDebugModeController(searchBarContract) }
    private val subjectListController by lazy { SubjectListController(subjectListContract) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllSubjectsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchSubjectList()
        clickBackButton()
        controllers()
        observers()
    }

    private fun fetchSubjectList() {
        setLoading(true)
        viewModel.getSubjectList()
    }

    private fun clickBackButton() {
        binding.backContainer.setOnClickListener {
            finish()
        }
    }

    private fun controllers() {
        handleSearchbarController()
        handleSubjectListController()
    }

    private fun handleSearchbarController() {
        binding.searchBarRv.apply {
            setController(searchBarController)
            layoutManager = LinearLayoutManager(context)
            requestModelBuild()
        }
    }

    private fun handleSubjectListController() {
        binding.subjectListRv.apply {
            setController(subjectListController)
            layoutManager = LinearLayoutManager(context)
            requestModelBuild()
        }
    }

    private fun observers() {
        viewModel.subjectList.observe(this) { result ->
            subjectListController.setSubjectList(result.toMutableList())
            setLoading(false)
        }

        viewModel.searchList.observe(this) { result ->
            subjectListController.setSubjectList(result.toMutableList())
            setLoading(false)
        }

        viewModel.subjectListError.observe(this) { error ->
            subjectListController.setResponseError(error)
        }

        viewModel.searchError.observe(this) { error ->
            subjectListController.setResponseError(error)
        }
    }

    private fun setLoading(status: Boolean) {
        subjectListController.setLoading(status)
        subjectListController.setResponseError(null)
    }

    private val searchBarContract = object : SearchBarContract {
        override fun clickFilterButtonListener() {
            startActivity(
                SelectOptionSubjectTypeActivity.newInstance(
                    checkboxRadioSelected,
                    checkboxSelectedList.toTypedArray(),
                    applicationContext
                )
            )
        }

        override fun clickSearchBarListener() {
            subjectListController.setSubjectList(mutableListOf())
        }

        override fun clickButtonCancelListener() {
            fetchSubjectList()
        }

        override fun editTextValue(text: String) {
            setLoading(true)
            viewModel.searchSubjectList(FirestoreDbConstants.Collections.SUBJECTS_LIST, text)
        }
    }

    private val subjectListContract = object : SubjectListContract {
        override fun clickEditOptionListener(id: String) {
            TODO("Not yet implemented")
        }

        override fun clickDeleteSubjectListener(id: String) {
            ConfirmationAlertDialog.saveBundle(
                id,
                "Excluir",
                "Caso confirme, essa matérias será deletada permanentemente, assim perdendo todos os dados. Deseja excluir?"
            )
            ConfirmationAlertDialog(confirmAlertContract).show(
                supportFragmentManager,
                ConfirmationAlertDialog.TAG
            )
        }
    }

    private val confirmAlertContract = object : ConfirmationAlertContract {
        override fun clickConfirmButtonListener(id: String) {
            TODO("Not yet implemented")
        }
    }

    companion object {
        var checkboxRadioSelected: String = ""
        var checkboxSelectedList: MutableList<String> = mutableListOf()

        @JvmStatic
        fun newInstance(context: Context): Intent {
            return Intent(context, AllSubjectsListActivity::class.java)
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