package com.example.savestudents.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    private var mFilterSubjectListWithPeriodAndShift = MutableLiveData<List<SubjectList>>()
    val filterSubjectListWithPeriodAndShift: LiveData<List<SubjectList>> = mFilterSubjectListWithPeriodAndShift

    override fun getSubjectList() {
        repository.getSubjectList(object : FirebaseResponseModel<List<SubjectListDto>> {
            override fun onSuccess(model: List<SubjectListDto>) {
                mSubjectList.value = model.asDomainModel()
            }

            override fun onFailure(error: OnFailureModel) {
                // TODO tratar erro na listagem
                val erro = ""
            }
        })
    }

    override fun filterSubjectListWithShift(
        collectionPath: String,
        field: String,
        values: MutableList<String>,
    ) {
        repository.getFilterOptions(
            collectionPath,
            field,
            values,
            object : FirebaseResponseModel<List<SubjectListDto>> {
                override fun onSuccess(model: List<SubjectListDto>) {
                    mFilterSubjectListShift.value = model.asDomainModel()
                }

                override fun onFailure(error: OnFailureModel) {}
            })
    }

    override fun filterSubjectListWithPeriod(
        collectionPath: String,
        field: String,
        values: MutableList<String>?,
    ) {
        if(!values.isNullOrEmpty()) {
            repository.getFilterOptions(
                collectionPath,
                field,
                values,
                object : FirebaseResponseModel<List<SubjectListDto>> {
                    override fun onSuccess(model: List<SubjectListDto>) {
                        mFilterSubjectListPeriod.value = model.asDomainModel()
                    }

                    override fun onFailure(error: OnFailureModel) {}
                })
        }
    }

    override fun filterSubjectListWithPeriodAndShift(
        collectionPath: String,
        field: String,
        values: MutableList<String>?,
        checkboxRadioSelected: String
    ) {
        if(!values.isNullOrEmpty()) {
            repository.getFilterOptions(
                collectionPath,
                field,
                values,
                object : FirebaseResponseModel<List<SubjectListDto>> {
                    override fun onSuccess(model: List<SubjectListDto>) {
                        // TODO implementar o shift como filtro para essa requisicao
                        val result = model.asDomainModel()

                        mFilterSubjectListWithPeriodAndShift.value = model.asDomainModel()
                    }

                    override fun onFailure(error: OnFailureModel) {}
                })
        }
    }
}