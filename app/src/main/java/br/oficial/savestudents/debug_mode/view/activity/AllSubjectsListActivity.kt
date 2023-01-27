package br.oficial.savestudents.debug_mode.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.br.core.constants.FirestoreDbConstants
import br.oficial.savestudents.databinding.ActivityAllSubjectsListBinding
import br.oficial.savestudents.debug_mode.constants.AllSubjectListConstants
import br.oficial.savestudents.debug_mode.controller.SearchBarDebugModeController
import br.oficial.savestudents.debug_mode.controller.SubjectListController
import br.oficial.savestudents.debug_mode.model.contract.SearchBarContract
import br.oficial.savestudents.debug_mode.model.contract.SubjectListContract
import br.oficial.savestudents.debug_mode.view.fragment.SubjectEditDialog
import br.oficial.savestudents.debug_mode.viewModel.AllSubjectsListViewModel
import br.oficial.savestudents.ui_component.confirmationAlertDialog.ConfirmationAlertContract
import br.oficial.savestudents.ui_component.confirmationAlertDialog.ConfirmationAlertDialog
import br.oficial.savestudents.view.activity.HomeActivity

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
        clickCreateSubject()
        clickBackButton()
        controllers()
        observers()
    }

    override fun onResume() {
        super.onResume()
        if (isFiltered) {
            handleFiltersSelected()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == EditSubjectActivity.EDIT_SUBJECT_REQUEST_CODE) {
            fetchSubjectList()
        }
    }


    private fun handleFiltersSelected() {
        subjectListController.clearSubjectList()

        if (checkboxRadioSelected.isBlank() && checkboxSelectedList.isEmpty()) {
            fetchSubjectList()
        }
        filterSubjectList()
    }

    private fun fetchSubjectList() {
        setLoading(true)
        viewModel.getSubjectList()
    }

    private fun filterSubjectList() {
        viewModel.filterSubjectList(
            FirestoreDbConstants.Collections.SUBJECTS_LIST,
            checkboxSelectedList,
            checkboxRadioSelected
        )
    }

    private fun clickBackButton() {
        binding.backContainer.setOnClickListener {
            val intent = HomeActivity.newInstance(applicationContext)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun clickCreateSubject() {
        binding.addTimelineButton.setOnClickListener {
            startActivity(CreateSubjectActivity.newInstance(applicationContext))
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
            SubjectEditDialog.saveBundle(id)
            SubjectEditDialog().show(supportFragmentManager, SubjectEditDialog.TAG)
        }

        override fun clickDeleteSubjectListener(id: String) {
            ConfirmationAlertDialog.saveBundle(
                id,
                AllSubjectListConstants.Dialog.DELETE_SUBJECT_TITLE,
                AllSubjectListConstants.Dialog.DELETE_SUBJECT_DESCRIPTION
            )

            ConfirmationAlertDialog(confirmAlertContract).show(
                supportFragmentManager,
                ConfirmationAlertDialog.TAG
            )
        }
    }

    private val confirmAlertContract = object : ConfirmationAlertContract {
        override fun clickConfirmButtonListener(id: String) {
            viewModel.deleteSubjectItem(id)
            fetchSubjectList()
        }
    }

    companion object {
        var checkboxRadioSelected: String = ""
        var checkboxSelectedList: MutableList<String> = mutableListOf()
        var isFiltered = false

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