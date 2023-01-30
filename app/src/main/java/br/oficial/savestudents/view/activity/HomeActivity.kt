package br.oficial.savestudents.view.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.oficial.savestudents.R
import com.br.core.constants.FirestoreDbConstants
import br.oficial.savestudents.controller.HeaderHomeActivityController
import br.oficial.savestudents.controller.HomeActivityController
import br.oficial.savestudents.controller.SearchBarController
import br.oficial.savestudents.databinding.ActivityHomeBinding
import br.oficial.savestudents.debug_mode.view.activity.AllSubjectsListActivity
import br.oficial.savestudents.debug_mode.view.activity.LoginActivity
import com.br.core.service.internal.dao.AdminCheckDAO
import br.oficial.savestudents.viewModel.HomeViewModel
import com.br.core.service.internal.database.AdminCheckDB
import com.example.data_transfer.model.contract.HeaderHomeActivityContract
import com.example.data_transfer.model.contract.HomeActivityContract
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var adminCheckDAO: AdminCheckDAO
    private val headerHomeActivityController by lazy { HeaderHomeActivityController(headerContract) }
    private val homeActivityController by lazy { HomeActivityController(homeContract) }
    private val searchBarController by lazy { SearchBarController(homeContract) }
    private lateinit var mViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adminCheckDAO = AdminCheckDB.getDataBase(applicationContext).adminCheckDAO()

        mViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(HomeViewModel()::class.java)

        fetchSubjectList()
        controllers()
        observers()
        handleFirebaseDynamicLinks(intent)
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

        adminCheckDAO.getAdminModeStatus()?.isAdminModeOn?.let {
            headerHomeActivityController.isAdminMode(it)
        }

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

    private fun handleFirebaseDynamicLinks(intent: Intent) {
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener { dynamicLinkData ->
                if (dynamicLinkData != null) {
                    showDynamicLinkOffer(dynamicLinkData.link)
                }
            }
            .addOnFailureListener(this) { e ->
                Log.d("DynamicLinkError", e.localizedMessage)
            }
    }

    private fun showDynamicLinkOffer(uri: Uri?) {
        val promotionCode = uri?.getQueryParameter("id")
        if (promotionCode.isNullOrBlank().not()) {
            startActivity(TimelineActivity.newInstance(applicationContext, promotionCode.toString()))
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

        override fun adminModeOnActiveListener() {
            val intent = AllSubjectsListActivity.newInstance(applicationContext)
            startActivity(intent)
        }

        override fun joinAdminModeListener() {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
    }

    private val homeContract = object : HomeActivityContract {
        override fun tryAgainListener() {
            fetchSubjectList()
        }

        override fun clickHorizontalCardListener(subjectId: String) {
            startActivity(TimelineActivity.newInstance(applicationContext, subjectId))
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