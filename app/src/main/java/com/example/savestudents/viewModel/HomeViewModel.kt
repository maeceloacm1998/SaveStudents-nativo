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
}