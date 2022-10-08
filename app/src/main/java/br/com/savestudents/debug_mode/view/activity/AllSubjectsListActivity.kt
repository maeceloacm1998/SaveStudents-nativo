package br.com.savestudents.debug_mode.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.savestudents.databinding.ActivityAllSubjectsListBinding
import br.com.savestudents.debug_mode.controller.SearchBarDebugModeController
import br.com.savestudents.debug_mode.controller.SubjectListController
import br.com.savestudents.debug_mode.model.contract.SearchBarContract
import br.com.savestudents.debug_mode.model.contract.SubjectListContract

class AllSubjectsListActivity : AppCompatActivity() {
    lateinit var binding: ActivityAllSubjectsListBinding
    private val searchBarController by lazy { SearchBarDebugModeController(searchBarContract) }
    private val subjectListController by lazy { SubjectListController(subjectListContract) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllSubjectsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controllers()
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

        override fun clickSearchBarListener() {}

        override fun clickButtonCancelListener() {}

        override fun editTextValue(text: String) {

        }
    }

    private val subjectListContract = object : SubjectListContract {
        override fun clickEditOptionListener(id: String) {
            TODO("Not yet implemented")
        }

        override fun clickDeleteSubjectListener(id: String) {
            TODO("Not yet implemented")
        }
    }

    companion object {
        var checkboxRadioSelected: String = ""
        var checkboxSelectedList: MutableList<String> = mutableListOf()
        var isFiltered = false

        @JvmStatic
        fun newInstance(context: Context, ): Intent {
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