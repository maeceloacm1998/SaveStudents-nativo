package com.example.savestudents.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.savestudents.constants.FirestoreDbConstants
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

    private val mHandleResponseError = MutableLiveData<Boolean>()
    val handleResponseError: LiveData<Boolean> = mHandleResponseError

    override fun getShiftOptions(collectionPath: String, orderByName: String) {
        repository.getFilterOptions(
            collectionPath,
            orderByName,
            object : FirebaseResponseModel<List<FilterOptionDTO>> {
                override fun onSuccess(model: List<FilterOptionDTO>) {
                    mShiftOptions.value = model.asDomainModel()
                    mHandleResponseError.value = false
                }

                override fun onFailure(error: OnFailureModel) {
                    handleError(error.code)
                }
            })
    }

    override fun getPeriodOptions(collectionPath: String, orderByName: String) {
        repository.getFilterOptions(
            collectionPath,
            orderByName,
            object : FirebaseResponseModel<List<FilterOptionDTO>> {
                override fun onSuccess(model: List<FilterOptionDTO>) {
                    mPeriodOptions.value = model.asDomainModel()
                }

                override fun onFailure(error: OnFailureModel) {
                    handleError(error.code)
                }
            })
    }

    private fun handleError(code: Int) {
        when (code) {
            FirestoreDbConstants.StatusCode.NOT_FOUND ->
                mHandleResponseError.value = true
        }
    }
}