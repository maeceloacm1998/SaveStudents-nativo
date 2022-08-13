package com.example.savestudents.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.savestudents.constants.HomeConstants
import com.example.savestudents.dto.SubjectListDto
import com.example.savestudents.dto.asDomainModel
import com.example.savestudents.model.SubjectList
import com.example.savestudents.model.repository.IHomeRepository
import com.example.savestudents.model.viewModel.IHomeViewModel
import com.example.savestudents.repository.HomeRepository
import com.example.savestudents.service.model.FirebaseResponseModel
import com.example.savestudents.service.model.OnFailureModel

class HomeViewModel() : ViewModel(), IHomeViewModel {

    private val repository: IHomeRepository = HomeRepository()

    private var mSubjectList = MutableLiveData<List<SubjectList>>()
    val subjectList: LiveData<List<SubjectList>> = mSubjectList

    private var mFilterSubjectListShift = MutableLiveData<List<SubjectList>>()
    val filterSubjectListShift: LiveData<List<SubjectList>> = mFilterSubjectListShift

    private var mFilterSubjectListPeriod = MutableLiveData<List<SubjectList>>()
    val filterSubjectListPeriod: LiveData<List<SubjectList>> = mFilterSubjectListPeriod

    private var mFilterSubjectListAllCategory = MutableLiveData<List<SubjectList>>()
    val filterSubjectListAllCategory: LiveData<List<SubjectList>> = mFilterSubjectListAllCategory

    override fun getSubjectList() {
        repository.getSubjectList(object : FirebaseResponseModel<List<SubjectListDto>> {
            override fun onSuccess(model: List<SubjectListDto>) {
                mSubjectList.value = model.asDomainModel()
            }

            override fun onFailure(error: OnFailureModel) {
                // TODO TRATAR ERROR
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

    private fun checkShiftAndPeriod(periodList: MutableList<String>?, shift: String): Boolean =
        shift.isNotBlank() && !periodList.isNullOrEmpty()

    private fun isShift(shift: String) = shift.isNotBlank()

    private fun isPeriod(periodList: MutableList<String>?) = !periodList.isNullOrEmpty()

    private fun filterPerShift(model: List<SubjectListDto>, shift: String): List<SubjectList> {
        return model.filter { result -> result.shift == shift }.asDomainModel()
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
                    // TODO TRATAR ERROR
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
                        // TODO TRATAR ERROR
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
                        // TODO TRATAR ERROR
                    }
                })
        }
    }
}