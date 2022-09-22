package br.com.savestudents.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.savestudents.constants.FirestoreDbConstants
import br.com.savestudents.dto.FilterOptionDTO
import br.com.savestudents.dto.asDomainModel
import br.com.savestudents.model.FilterOption
import br.com.savestudents.model.repository.IFilterOptionsRepository
import br.com.savestudents.model.viewModel.IFilterOptionsViewModel
import br.com.savestudents.repository.FilterOptionsRepository
import br.com.savestudents.service.external.model.FirebaseResponseModel
import br.com.savestudents.service.external.model.OnFailureModel

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