package com.example.savestudents.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.savestudents.dto.FilterOptionDTO
import com.example.savestudents.dto.asDomainModel
import com.example.savestudents.model.FilterOption
import com.example.savestudents.model.repository.IFilterOptionsRepository
import com.example.savestudents.model.viewModel.IFilterOptionsViewModel
import com.example.savestudents.repository.FilterOptionsRepository
import com.example.savestudents.service.model.FirebaseResponseModel
import com.example.savestudents.service.model.OnFailureModel

class FilterOptionsViewModel : ViewModel(), IFilterOptionsViewModel {
    private val repository: IFilterOptionsRepository = FilterOptionsRepository()

    private val mShiftOptions = MutableLiveData<List<FilterOption>>()
    val shiftOptions: LiveData<List<FilterOption>> = mShiftOptions

    private val mPeriodOptions = MutableLiveData<List<FilterOption>>()
    val periodOptions: LiveData<List<FilterOption>> = mPeriodOptions

    override fun getShiftOptions(collectionPath: String) {
        repository.getFilterOptions(
            collectionPath,
            object : FirebaseResponseModel<List<FilterOptionDTO>> {
                override fun onSuccess(model: List<FilterOptionDTO>) {
                    mShiftOptions.value = model.asDomainModel()
                }

                override fun onFailure(error: OnFailureModel) {
                    TODO("Not yet implemented")
                }
            })
    }

    override fun getPeriodOptions(collectionPath: String) {
        repository.getFilterOptions(
            collectionPath,
            object : FirebaseResponseModel<List<FilterOptionDTO>> {
                override fun onSuccess(model: List<FilterOptionDTO>) {
                    mPeriodOptions.value = model.asDomainModel()
                }

                override fun onFailure(error: OnFailureModel) {
                    TODO("Not yet implemented")
                }
            })
    }
}