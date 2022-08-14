package com.example.savestudents.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.savestudents.constants.FirestoreDbConstants
import com.example.savestudents.constants.HomeConstants
import com.example.savestudents.dto.SubjectListDto
import com.example.savestudents.dto.asDomainModel
import com.example.savestudents.model.SubjectList
import com.example.savestudents.model.error.SubjectListErrorModel
import com.example.savestudents.model.repository.IHomeRepository
import com.example.savestudents.model.viewModel.IHomeViewModel
import com.example.savestudents.repository.HomeRepository
import com.example.savestudents.service.model.FirebaseResponseModel
import com.example.savestudents.service.model.OnFailureModel

class HomeViewModel() : ViewModel(), IHomeViewModel {

    private val repository: IHomeRepository = HomeRepository()

    private var mSubjectList = MutableLiveData<List<SubjectList>>()
    val subjectList: LiveData<List<SubjectList>> = mSubjectList

    private var mSearchList = MutableLiveData<List<SubjectList>>()
    val searchList: LiveData<List<SubjectList>> = mSearchList

    private var mFilterSubjectListShift = MutableLiveData<List<SubjectList>>()
    val filterSubjectListShift: LiveData<List<SubjectList>> = mFilterSubjectListShift

    private var mFilterSubjectListPeriod = MutableLiveData<List<SubjectList>>()
    val filterSubjectListPeriod: LiveData<List<SubjectList>> = mFilterSubjectListPeriod

    private var mFilterSubjectListAllCategory = MutableLiveData<List<SubjectList>>()
    val filterSubjectListAllCategory: LiveData<List<SubjectList>> = mFilterSubjectListAllCategory

    private var mSubjectListError = MutableLiveData<SubjectListErrorModel>()
    var subjectListError: LiveData<SubjectListErrorModel> = mSubjectListError

    private var mSearchError = MutableLiveData<SubjectListErrorModel>()
    var searchError: LiveData<SubjectListErrorModel> = mSearchError

    override fun getSubjectList() {
        repository.getSubjectList(object : FirebaseResponseModel<List<SubjectListDto>> {
            override fun onSuccess(model: List<SubjectListDto>) {
                mSubjectList.value = model.asDomainModel()
            }

            override fun onFailure(error: OnFailureModel) {
                handleErrorSubjectList(
                    error.code, HomeConstants.Filter.TYPE_LIST_ERROR,
                    HomeConstants.Filter.MESSAGE_ERROR, ""
                )
            }
        })
    }

    override fun filterSubjectList(
        collectionPath: String,
        periodList: MutableList<String>?,
        shift: String
    ) {
        if (checkShiftAndPeriod(periodList, shift)) {
            filterSubjectListAllCategory(collectionPath, periodList!!, shift)
        } else {
            filterPerCategory(collectionPath, periodList, shift)
        }
    }

    override fun searchSubjectList(collectionPath: String, searchValue: String) {
        repository.getSubjectList(object : FirebaseResponseModel<List<SubjectListDto>> {
            override fun onSuccess(model: List<SubjectListDto>) {
                if (searchValue.isBlank()) {
                    mSearchList.value = mutableListOf()
                    return
                }

                if (!containsSearchValue(model, searchValue)) {
                    handleErrorSearch(
                        FirestoreDbConstants.StatusCode.NOT_FOUND,
                        HomeConstants.Filter.TYPE_SEARCH_ERROR,
                        HomeConstants.Filter.MESSAGE_ERROR,
                        HomeConstants.Filter.DESCRIPTION_SEARCH_ERROR
                    )
                    return
                }

                mSearchList.value = filterSearchValue(model, searchValue).asDomainModel()
            }

            override fun onFailure(error: OnFailureModel) {
                handleErrorSearch(
                    error.code, HomeConstants.Filter.TYPE_LIST_ERROR,
                    HomeConstants.Filter.MESSAGE_ERROR, ""
                )
            }
        })
    }

    private fun filterSubjectListAllCategory(
        collectionPath: String,
        periodList: MutableList<String>,
        shift: String
    ) {
        repository.getFilterOptions(
            collectionPath,
            HomeConstants.Filter.PERIOD_FIELD,
            periodList,
            object : FirebaseResponseModel<List<SubjectListDto>> {
                override fun onSuccess(model: List<SubjectListDto>) {
                    mFilterSubjectListAllCategory.value = filterPerShift(model, shift)
                }

                override fun onFailure(error: OnFailureModel) {
                    handleErrorSubjectList(
                        error.code,
                        HomeConstants.Filter.TYPE_FILTER_ERROR,
                        HomeConstants.Filter.MESSAGE_ERROR,
                        HomeConstants.Filter.DESCRIPTION_ERROR
                    )
                }
            })
    }

    private fun filterPerCategory(
        collectionPath: String,
        periodList: MutableList<String>?,
        shift: String
    ) {
        if (isShift(shift)) {
            repository.getFilterOptions(
                collectionPath,
                HomeConstants.Filter.SHIFT_FIELD,
                mutableListOf(shift),
                object : FirebaseResponseModel<List<SubjectListDto>> {
                    override fun onSuccess(model: List<SubjectListDto>) {
                        mFilterSubjectListShift.value = model.asDomainModel()
                    }

                    override fun onFailure(error: OnFailureModel) {
                        handleErrorSubjectList(
                            error.code,
                            HomeConstants.Filter.TYPE_FILTER_ERROR,
                            HomeConstants.Filter.MESSAGE_ERROR,
                            HomeConstants.Filter.DESCRIPTION_ERROR
                        )
                    }
                })
        }

        if (isPeriod(periodList)) {
            repository.getFilterOptions(
                collectionPath,
                HomeConstants.Filter.PERIOD_FIELD,
                periodList!!,
                object : FirebaseResponseModel<List<SubjectListDto>> {
                    override fun onSuccess(model: List<SubjectListDto>) {
                        mFilterSubjectListPeriod.value = model.asDomainModel()
                    }

                    override fun onFailure(error: OnFailureModel) {
                        handleErrorSubjectList(
                            error.code,
                            HomeConstants.Filter.TYPE_FILTER_ERROR,
                            HomeConstants.Filter.MESSAGE_ERROR,
                            HomeConstants.Filter.DESCRIPTION_ERROR
                        )
                    }
                })
        }
    }

    private fun containsSearchValue(model: List<SubjectListDto>, searchValue: String): Boolean {
        return model.find { result ->
            result.title.lowercase().contains(searchValue.lowercase())
        } != null
    }

    private fun filterSearchValue(
        model: List<SubjectListDto>,
        searchValue: String
    ): List<SubjectListDto> {
        return model.filter { result ->
            result.title.lowercase().contains(searchValue.lowercase())
        }
    }

    private fun checkShiftAndPeriod(periodList: MutableList<String>?, shift: String): Boolean =
        shift.isNotBlank() && !periodList.isNullOrEmpty()

    private fun isShift(shift: String) = shift.isNotBlank()

    private fun isPeriod(periodList: MutableList<String>?) = !periodList.isNullOrEmpty()

    private fun filterPerShift(model: List<SubjectListDto>, shift: String): List<SubjectList> {
        return model.filter { result -> result.shift == shift }.asDomainModel()
    }

    private fun handleErrorSubjectList(
        code: Int,
        type: String,
        message: String,
        description: String
    ) {
        when (code) {
            FirestoreDbConstants.StatusCode.NOT_FOUND ->
                mSubjectListError.value = SubjectListErrorModel(type, message, description)
        }
    }

    private fun handleErrorSearch(code: Int, type: String, message: String, description: String) {
        when (code) {
            FirestoreDbConstants.StatusCode.NOT_FOUND ->
                mSearchError.value = SubjectListErrorModel(type, message, description)
        }
    }
}