package br.oficial.savestudents.debug_mode.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.oficial.savestudents.constants.FirestoreDbConstants
import br.oficial.savestudents.debug_mode.constants.AllSubjectListConstants
import br.oficial.savestudents.debug_mode.model.repository.IAllSubjectList
import br.oficial.savestudents.debug_mode.repository.AllSubjectsListRepository
import br.oficial.savestudents.dto.SubjectListDto
import br.oficial.savestudents.dto.asDomainModel
import br.oficial.savestudents.model.SubjectList
import br.oficial.savestudents.model.error.SubjectListErrorModel
import br.oficial.savestudents.model.repository.IHomeRepository
import br.oficial.savestudents.model.viewModel.IHomeViewModel
import br.oficial.savestudents.repository.HomeRepository
import br.oficial.savestudents.service.external.model.FirebaseResponseModel
import br.oficial.savestudents.service.external.model.OnFailureModel

class AllSubjectsListViewModel(mContext: Context): ViewModel(), IHomeViewModel, IAllSubjectList {
    private val repository: IHomeRepository = HomeRepository()
    private val repositoryAllSubjectList: IAllSubjectList = AllSubjectsListRepository()

    private var mSubjectList = MutableLiveData<List<SubjectList>>()
    val subjectList: LiveData<List<SubjectList>> = mSubjectList

    private var mSearchList = MutableLiveData<List<SubjectList>>()
    val searchList: LiveData<List<SubjectList>> = mSearchList

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
                    error.code, AllSubjectListConstants.Filter.TYPE_LIST_ERROR,
                    AllSubjectListConstants.Filter.MESSAGE_ERROR, ""
                )
            }
        })
    }

    override fun filterSubjectList(
        collectionPath: String,
        periodList: MutableList<String>?,
        shift: String
    ) {
        if (selectPeriodAndShift(periodList, shift)) {
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
                        AllSubjectListConstants.Filter.TYPE_SEARCH_ERROR,
                        AllSubjectListConstants.Filter.MESSAGE_ERROR,
                        AllSubjectListConstants.Filter.DESCRIPTION_SEARCH_ERROR
                    )
                    return
                }

                mSearchList.value = filterSearchValue(model, searchValue).asDomainModel()
            }

            override fun onFailure(error: OnFailureModel) {
                handleErrorSearch(
                    error.code, AllSubjectListConstants.Filter.TYPE_LIST_ERROR,
                    AllSubjectListConstants.Filter.MESSAGE_ERROR, ""
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
            AllSubjectListConstants.Filter.PERIOD_FIELD,
            periodList,
            object : FirebaseResponseModel<List<SubjectListDto>> {
                override fun onSuccess(model: List<SubjectListDto>) {
                    if (!containsShiftValue(model, shift)) {
                        handleErrorSubjectList(
                            FirestoreDbConstants.StatusCode.NOT_FOUND,
                            AllSubjectListConstants.Filter.TYPE_FILTER_ERROR,
                            AllSubjectListConstants.Filter.MESSAGE_ERROR,
                            AllSubjectListConstants.Filter.DESCRIPTION_ERROR
                        )
                        return
                    }

                    mSubjectList.value = filterPerShift(model, shift)
                }

                override fun onFailure(error: OnFailureModel) {
                    handleErrorSubjectList(
                        error.code,
                        AllSubjectListConstants.Filter.TYPE_FILTER_ERROR,
                        AllSubjectListConstants.Filter.MESSAGE_ERROR,
                        AllSubjectListConstants.Filter.DESCRIPTION_ERROR
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
                AllSubjectListConstants.Filter.SHIFT_FIELD,
                mutableListOf(shift),
                object : FirebaseResponseModel<List<SubjectListDto>> {
                    override fun onSuccess(model: List<SubjectListDto>) {
                        mSubjectList.value = model.asDomainModel()
                    }

                    override fun onFailure(error: OnFailureModel) {
                        handleErrorSubjectList(
                            error.code,
                            AllSubjectListConstants.Filter.TYPE_FILTER_ERROR,
                            AllSubjectListConstants.Filter.MESSAGE_ERROR,
                            AllSubjectListConstants.Filter.DESCRIPTION_ERROR
                        )
                    }
                })
        }

        if (isPeriod(periodList)) {
            repository.getFilterOptions(
                collectionPath,
                AllSubjectListConstants.Filter.PERIOD_FIELD,
                periodList!!,
                object : FirebaseResponseModel<List<SubjectListDto>> {
                    override fun onSuccess(model: List<SubjectListDto>) {
                        mSubjectList.value = model.asDomainModel()
                    }

                    override fun onFailure(error: OnFailureModel) {
                        handleErrorSubjectList(
                            error.code,
                            AllSubjectListConstants.Filter.TYPE_FILTER_ERROR,
                            AllSubjectListConstants.Filter.MESSAGE_ERROR,
                            AllSubjectListConstants.Filter.DESCRIPTION_ERROR
                        )
                    }
                })
        }
    }

    override fun deleteSubjectItem(id: String) {
        repositoryAllSubjectList.deleteSubjectItem(id)
    }

    private fun containsSearchValue(model: List<SubjectListDto>, searchValue: String): Boolean {
        return model.find { result ->
            result.subjectName.lowercase().contains(searchValue.lowercase())
        } != null
    }

    private fun filterSearchValue(
        model: List<SubjectListDto>,
        searchValue: String
    ): List<SubjectListDto> {
        return model.filter { result ->
            result.subjectName.lowercase().contains(searchValue.lowercase())
        }
    }

    private fun selectPeriodAndShift(periodList: MutableList<String>?, shift: String): Boolean =
        shift.isNotBlank() && !periodList.isNullOrEmpty()

    private fun isShift(shift: String) = shift.isNotBlank()

    private fun isPeriod(periodList: MutableList<String>?) = !periodList.isNullOrEmpty()

    private fun containsShiftValue(model: List<SubjectListDto>, shift: String): Boolean {
        return model.find { result -> result.shift == shift } != null
    }

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