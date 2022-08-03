package com.example.savestudents.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.savestudents.dto.SubjectListDto
import com.example.savestudents.model.IHomeRepository
import com.example.savestudents.repository.HomeRepository
import com.example.savestudents.service.model.FirebaseResponseModel
import com.example.savestudents.service.model.OnFailureModel

class HomeViewModel() : ViewModel() {
    private val repository: IHomeRepository = HomeRepository()

    private var mSubjectList = MutableLiveData<List<SubjectListDto>>()
    val subjectList: LiveData<List<SubjectListDto>> = mSubjectList

    fun getSubjectList() {
        repository.getSubjectList(object : FirebaseResponseModel<List<SubjectListDto>> {
            override fun onSuccess(model: List<SubjectListDto>) {
                mSubjectList.value = model
            }

            override fun onFailure(error: OnFailureModel) {
                val erro = ""
            }
        })
    }

}